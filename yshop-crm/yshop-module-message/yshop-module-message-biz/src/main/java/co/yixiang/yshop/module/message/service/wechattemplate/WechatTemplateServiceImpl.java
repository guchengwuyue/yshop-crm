package co.yixiang.yshop.module.message.service.wechattemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import co.yixiang.yshop.module.message.controller.admin.wechattemplate.vo.*;
import co.yixiang.yshop.module.message.dal.dataobject.wechattemplate.WechatTemplateDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;

import co.yixiang.yshop.module.message.convert.wechattemplate.WechatTemplateConvert;
import co.yixiang.yshop.module.message.dal.mysql.wechattemplate.WechatTemplateMapper;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.message.enums.ErrorCodeConstants.*;

/**
 * 微信模板 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class WechatTemplateServiceImpl extends ServiceImpl<WechatTemplateMapper, WechatTemplateDO> implements WechatTemplateService {

    @Resource
    private WechatTemplateMapper wechatTemplateMapper;

    @Override
    public Integer createWechatTemplate(WechatTemplateCreateReqVO createReqVO) {
        // 插入
        WechatTemplateDO wechatTemplate = WechatTemplateConvert.INSTANCE.convert(createReqVO);
        wechatTemplateMapper.insert(wechatTemplate);
        // 返回
        return wechatTemplate.getId();
    }

    @Override
    public void updateWechatTemplate(WechatTemplateUpdateReqVO updateReqVO) {
        // 校验存在
        validateWechatTemplateExists(updateReqVO.getId());
        // 更新
        WechatTemplateDO updateObj = WechatTemplateConvert.INSTANCE.convert(updateReqVO);
        wechatTemplateMapper.updateById(updateObj);
    }

    @Override
    public void deleteWechatTemplate(Integer id) {
        // 校验存在
        validateWechatTemplateExists(id);
        // 删除
        wechatTemplateMapper.deleteById(id);
    }

    private void validateWechatTemplateExists(Integer id) {
        if (wechatTemplateMapper.selectById(id) == null) {
            throw exception(WECHAT_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public WechatTemplateDO getWechatTemplate(Integer id) {
        return wechatTemplateMapper.selectById(id);
    }

    @Override
    public List<WechatTemplateDO> getWechatTemplateList(Collection<Integer> ids) {
        return wechatTemplateMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<WechatTemplateDO> getWechatTemplatePage(WechatTemplatePageReqVO pageReqVO) {
        return wechatTemplateMapper.selectPage(pageReqVO);
    }

    @Override
    public List<WechatTemplateDO> getWechatTemplateList(WechatTemplateExportReqVO exportReqVO) {
        return wechatTemplateMapper.selectList(exportReqVO);
    }

}
