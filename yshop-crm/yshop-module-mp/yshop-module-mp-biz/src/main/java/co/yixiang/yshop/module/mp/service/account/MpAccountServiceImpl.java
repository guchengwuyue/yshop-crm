package co.yixiang.yshop.module.mp.service.account;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import co.yixiang.yshop.framework.common.exception.ErrorCode;
import co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.tenant.core.context.TenantContextHolder;
import co.yixiang.yshop.framework.tenant.core.util.TenantUtils;
import co.yixiang.yshop.module.mp.controller.admin.account.vo.MpAccountCreateReqVO;
import co.yixiang.yshop.module.mp.controller.admin.account.vo.MpAccountPageReqVO;
import co.yixiang.yshop.module.mp.controller.admin.account.vo.MpAccountUpdateReqVO;
import co.yixiang.yshop.module.mp.convert.account.MpAccountConvert;
import co.yixiang.yshop.module.mp.dal.dataobject.account.MpAccountDO;
import co.yixiang.yshop.module.mp.dal.mysql.account.MpAccountMapper;
import co.yixiang.yshop.module.mp.enums.ErrorCodeConstants;
import co.yixiang.yshop.module.mp.enums.MpAccountEnum;
import co.yixiang.yshop.module.mp.framework.mp.core.MpServiceFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.binarywang.spring.starter.wxjava.mp.properties.WxMpProperties;
import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.framework.common.util.collection.CollectionUtils.convertMap;
import static co.yixiang.yshop.framework.common.util.collection.CollectionUtils.getMaxValue;
import static co.yixiang.yshop.module.system.enums.ErrorCodeConstants.USER_USERNAME_EXISTS;

/**
 * 公众号账号 Service 实现类
 *
 * @author fengdan
 */
@Slf4j
@Service
@Validated
public class MpAccountServiceImpl implements MpAccountService {

    /**
     * 账号缓存
     * key：账号编号 {@link MpAccountDO#getAppId()}
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<String, MpAccountDO> accountCache;

    @Resource
    private MpAccountMapper mpAccountMapper;

    @Resource
    @Lazy // 延迟加载，解决循环依赖的问题
    private MpServiceFactory mpServiceFactory;

    @Resource
    private RedisTemplateWxRedisOps redisTemplateWxRedisOps;
    @Resource
    private WxMpProperties mpProperties;

    @Override
    @PostConstruct
    public void initLocalCache() {
        // 注意：忽略自动多租户，因为要全局初始化缓存
        TenantUtils.executeIgnore(() -> {
            // 第一步：查询数据
            List<MpAccountDO> accounts = Collections.emptyList();
            try {
                accounts = mpAccountMapper.selectList();
            } catch (Throwable ex) {
                if (!ex.getMessage().contains("doesn't exist")) {
                    throw ex;
                }
                log.error("[微信公众号 yshop-module-mp - 表结构未导入][参考 https://www.yixiang.co/mp/build/ 开启]");
            }
            log.info("[initLocalCacheIfUpdate][缓存公众号账号，数量为:{}]", accounts.size());

            // 第二步：构建缓存。创建或更新支付 Client
            mpServiceFactory.init(accounts);
            accountCache = convertMap(accounts, MpAccountDO::getAppId);
        });
    }

    /**
     * 通过定时任务轮询，刷新缓存
     *
     * 目的：多节点部署时，通过轮询”通知“所有节点，进行刷新
     */
    @Scheduled(initialDelay = 60, fixedRate = 60, timeUnit = TimeUnit.SECONDS)
    public void refreshLocalCache() {
        // 注意：忽略自动多租户，因为要全局初始化缓存
        TenantUtils.executeIgnore(() -> {
            // 情况一：如果缓存里没有数据，则直接刷新缓存
            if (CollUtil.isEmpty(accountCache)) {
                initLocalCache();
                return;
            }

            // 情况二，如果缓存里数据，则通过 updateTime 判断是否有数据变更，有变更则刷新缓存
            LocalDateTime maxTime = getMaxValue(accountCache.values(), MpAccountDO::getUpdateTime);
            if (mpAccountMapper.selectCountByUpdateTimeGt(maxTime) > 0) {
                initLocalCache();
            }
        });
    }

    @Override
    public Long createAccount(MpAccountCreateReqVO createReqVO) {
        // 校验 appId 唯一
        validateAppIdUnique(null, createReqVO.getAppId());

        // 插入
        MpAccountDO account = MpAccountConvert.INSTANCE.convert(createReqVO);
        mpAccountMapper.insert(account);

        // 刷新缓存
        initLocalCache();
        return account.getId();
    }

    @Override
    public void updateAccount(MpAccountUpdateReqVO updateReqVO) {
        // 校验存在
        validateAccountExists(updateReqVO.getId());
        // 校验 appId 唯一
        validateAppIdUnique(updateReqVO.getId(), updateReqVO.getAppId());

        // 更新
        MpAccountDO updateObj = MpAccountConvert.INSTANCE.convert(updateReqVO);
        mpAccountMapper.updateById(updateObj);

        // 刷新缓存
        initLocalCache();
    }

    @Override
    public void deleteAccount(Long id) {
        // 校验存在
        validateAccountExists(id);
        // 删除
        mpAccountMapper.deleteById(id);

        // 刷新缓存
        initLocalCache();
    }

    private MpAccountDO validateAccountExists(Long id) {
        MpAccountDO account = mpAccountMapper.selectById(id);
        if (account == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.ACCOUNT_NOT_EXISTS);
        }
        return account;
    }

    @VisibleForTesting
    public void validateAppIdUnique(Long id, String appId) {
        // 多个租户，appId 是不能重复，否则公众号回调会无法识别
        TenantUtils.executeIgnore(() -> {
            MpAccountDO account = mpAccountMapper.selectByAppId(appId);
            if (account == null) {
                return;
            }
            // 存在 account 记录的情况下
            if (id == null // 新增时，说明重复
                    || ObjUtil.notEqual(id, account.getId())) { // 更新时，如果 id 不一致，说明重复
                throw exception(USER_USERNAME_EXISTS);
            }
        });
    }

    @Override
    public MpAccountDO getAccount(Long id) {
        return mpAccountMapper.selectById(id);
    }

    @Override
    public MpAccountDO getAccountFromCache(String appId) {
        return accountCache.get(appId);
    }

    @Override
    public PageResult<MpAccountDO> getAccountPage(MpAccountPageReqVO pageReqVO) {
        return mpAccountMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MpAccountDO> getAccountList() {
        return mpAccountMapper.selectList();
    }

    @Override
    public void generateAccountQrCode(Long id) {
        // 校验存在
        MpAccountDO account = validateAccountExists(id);

        // 生成二维码
        WxMpService mpService = mpServiceFactory.getRequiredMpService(account.getAppId());
        String qrCodeUrl;
        try {
            WxMpQrCodeTicket qrCodeTicket = mpService.getQrcodeService().qrCodeCreateLastTicket("default");
            qrCodeUrl = mpService.getQrcodeService().qrCodePictureUrl(qrCodeTicket.getTicket());
        } catch (WxErrorException e) {
            throw exception(ErrorCodeConstants.ACCOUNT_GENERATE_QR_CODE_FAIL, e.getError().getErrorMsg());
        }

        // 保存二维码
        mpAccountMapper.updateById(new MpAccountDO().setId(id).setQrCodeUrl(qrCodeUrl));
    }

    @Override
    public void clearAccountQuota(Long id) {
        // 校验存在
        MpAccountDO account = validateAccountExists(id);

        // 生成二维码
        WxMpService mpService = mpServiceFactory.getRequiredMpService(account.getAppId());
        try {
            mpService.clearQuota(account.getAppId());
        } catch (WxErrorException e) {
            throw exception(ErrorCodeConstants.ACCOUNT_CLEAR_QUOTA_FAIL, e.getError().getErrorMsg());
        }
    }


    @Override
    public void setMain(Long id, Integer isMini) {
        Long tenantId = TenantContextHolder.getTenantId();
        LambdaQueryWrapper<MpAccountDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MpAccountDO::getId,id).eq(MpAccountDO::getIsMiapp,isMini);
        LambdaQueryWrapper<MpAccountDO> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.ne(MpAccountDO::getId,id).eq(MpAccountDO::getIsMiapp,isMini);
        MpAccountDO mpAccountDO = MpAccountDO.builder().isMain(MpAccountEnum.IS_MAIN_0.getValue()).build();
        MpAccountDO mpAccountDO2 = MpAccountDO.builder().isMain(MpAccountEnum.IS_MAIN_1.getValue()).build();
        //先更新其他未不是主账户
        mpAccountMapper.update(mpAccountDO,wrapper2);
        //然后设置主账户
        mpAccountMapper.update(mpAccountDO2,wrapper);



    }

    @Override
    public WxMaService getMaService() {
        MpAccountDO account = mpAccountMapper.selectOne(new LambdaQueryWrapper<MpAccountDO>()
                .eq(MpAccountDO::getIsMiapp,MpAccountEnum.IS_MINI_1.getValue())
                .eq(MpAccountDO::getIsMain,MpAccountEnum.IS_MAIN_1.getValue()));
        if(account == null) {
            throw exception(new ErrorCode(10086,"请设置小程序主账户"));
        }
        WxMaDefaultConfigImpl maDefaultConfig = new WxMaDefaultConfigImpl();
        maDefaultConfig.setAppid(account.getAppId());
        maDefaultConfig.setSecret(account.getAppSecret());
        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(maDefaultConfig);
        return wxMaService;
    }

    @Override
    public WxMpService getWechatService() {
        MpAccountDO account = mpAccountMapper.selectOne(new LambdaQueryWrapper<MpAccountDO>()
                .eq(MpAccountDO::getIsMiapp, MpAccountEnum.IS_MINI_0.getValue())
                .eq(MpAccountDO::getIsMain,MpAccountEnum.IS_MAIN_1.getValue()));
        if(account == null) {
            throw exception(new ErrorCode(10086,"请设置公众号主账户"));
        }
        WxMpRedisConfigImpl configStorage = new WxMpRedisConfigImpl(
                redisTemplateWxRedisOps, mpProperties.getConfigStorage().getKeyPrefix());
        configStorage.setAppId(account.getAppId());
        configStorage.setSecret(account.getAppSecret());
        configStorage.setToken(account.getToken());
        configStorage.setAesKey(account.getAesKey());

        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(configStorage);
        return service;
    }

}
