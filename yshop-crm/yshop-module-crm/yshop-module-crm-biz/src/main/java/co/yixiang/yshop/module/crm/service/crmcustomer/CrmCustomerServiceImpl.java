package co.yixiang.yshop.module.crm.service.crmcustomer;

import cn.hutool.core.util.StrUtil;
import co.yixiang.yshop.framework.common.enums.UserTypeEnum;
import co.yixiang.yshop.framework.common.exception.ErrorCode;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomercontacts.CrmCustomerContactsDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomercontacts.CrmCustomerContactsMapper;
import co.yixiang.yshop.module.crm.enums.CustomerTypesEnum;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.crm.service.crmoperatelog.CrmOperatelogService;
import co.yixiang.yshop.module.crm.service.crmrecord.CrmRecordService;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import co.yixiang.yshop.module.system.service.mail.MailSendService;
import co.yixiang.yshop.module.system.service.sms.SmsSendService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.CUSTOMER_NOT_EXISTS;

/**
 * 客户 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmCustomerServiceImpl implements CrmCustomerService {

    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmOperatelogService crmOperatelogService;
    @Resource
    private CrmCustomerContactsMapper customerContactsMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private SmsSendService smsSendService;
    @Resource
    private MailSendService mailSendService;
    @Resource
    private CrmRecordService crmRecordService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCustomer(CrmCustomerSaveReqVO createReqVO) {


        // 插入
        CrmCustomerDO customer = BeanUtils.toBean(createReqVO, CrmCustomerDO.class);
        customer.setCollectTime(LocalDateTime.now());
        customer.setFollowTime(LocalDateTime.now());
        customer.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        customerMapper.insert(customer);

        //创建联系人
        CrmCustomerContactsDO crmCustomerContactsDO = CrmCustomerContactsDO.builder()
                .customerId(customer.getId())
                .ownerUserId(SecurityFrameworkUtils.getLoginUserId())
                .name(createReqVO.getName())
                .mobile(createReqVO.getMobile())
                .telephone(createReqVO.getTelephone())
                .remark(createReqVO.getRemark())
                .wechat(createReqVO.getWeixin())
                .build();
        customerContactsMapper.insert(crmCustomerContactsDO);


        //插入日志
        crmOperatelogService.createLog("添加客户",customer.getId(),0L,0L);
        // 返回
        return customer.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomer(CrmCustomerSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerExists(updateReqVO.getId());
        // 更新
        CrmCustomerDO updateObj = BeanUtils.toBean(updateReqVO, CrmCustomerDO.class);
        customerMapper.updateById(updateObj);

        //插入日志
        crmOperatelogService.createLog("修改客户",updateReqVO.getId(),0L,0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomer(Long id) {
        // 校验存在
        validateCustomerExists(id);
        // 删除
        customerMapper.deleteById(id);

        crmRecordService.deleteByType(TypesEnum.CUSTOMER.getValue(),id);

        //插入日志
        crmOperatelogService.createLog("删除客户",id,0L,0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void openCustomer(Long id) {
        CrmCustomerDO customerDO = customerMapper.selectById(id);
        if(customerDO == null){
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        //设置拥有0
        customerDO.setOwnerUserId(0L);
        customerMapper.updateById(customerDO);
        //设置联系人的拥有0
        customerContactsMapper.update(CrmCustomerContactsDO.builder().ownerUserId(0L).build(),
                new LambdaQueryWrapper<CrmCustomerContactsDO>().eq(CrmCustomerContactsDO::getCustomerId,id));
        crmRecordService.deleteByType(TypesEnum.CUSTOMER.getValue(),id);
        //插入日志
        crmOperatelogService.createLog("放入公海",id,0L,0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveCustomer(Long id) {
        CrmCustomerDO customerDO = customerMapper.selectById(id);
        if(customerDO == null){
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        customerDO.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        customerDO.setCollectTime(LocalDateTime.now());
        customerDO.setFollowTime(LocalDateTime.now());
        customerMapper.updateById(customerDO);

        //更新联系人
        customerContactsMapper.update(CrmCustomerContactsDO.builder()
                        .ownerUserId(SecurityFrameworkUtils.getLoginUserId()).build(),
                new LambdaQueryWrapper<CrmCustomerContactsDO>().eq(CrmCustomerContactsDO::getCustomerId,id));

        //插入日志
        crmOperatelogService.createLog("公海领取客户",id,0L,0L);
    }

    private void validateCustomerExists(Long id) {
        if (customerMapper.selectById(id) == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
    }

    @Override
    public CrmCustomerDO getCustomer(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public PageResult<CrmCustomerRespVO> getCustomerPage(CrmCustomerPageReqVO pageReqVO) {
        List<Long> ids = new ArrayList<>();
        Long adminId = SecurityFrameworkUtils.getLoginUserId();
        if(CustomerTypesEnum.OPEN.getValue().equals(pageReqVO.getType())){
            ids.add(0L);
        }else{
            if(RelationEnum.MY.getValue().equals(pageReqVO.getRelation())){
                ids.add(adminId);
            }else if(RelationEnum.SUB.getValue().equals(pageReqVO.getRelation())){
                ids = adminUserApi.getUserListBySubordinateIds(adminId);
            }
        }
        return customerMapper.selectPage2(pageReqVO,ids);
    }


    @Override
    public PageResult<CrmCustomerRespVO> getCustomerPage2(CrmCustomerPageReqVO pageReqVO) {
        return customerMapper.selectPage2(pageReqVO,new ArrayList<>());
    }


    @Override
    public void  sendSms(SmsTemplateSendVO sendVO) {
        List<CrmCustomerContactsDO> crmCustomerDOS = null;
        if(sendVO.getIsCustomer()){
            crmCustomerDOS = customerContactsMapper.selectList(new LambdaQueryWrapper<CrmCustomerContactsDO>().
                    in(CrmCustomerContactsDO::getCustomerId,sendVO.getCustomerIds()));
        }else{
            crmCustomerDOS = customerContactsMapper.selectList(new LambdaQueryWrapper<CrmCustomerContactsDO>().
                    in(CrmCustomerContactsDO::getId,sendVO.getCustomerIds()));
        }

        if(crmCustomerDOS == null || crmCustomerDOS.isEmpty()){
            throw exception(new ErrorCode(202409100,"客户信息不存在"));
        }
        crmCustomerDOS.forEach(customer -> {
            //发送短信
            if(StrUtil.isNotEmpty(customer.getMobile())){
                smsSendService.sendSingleSms(customer.getMobile(),customer.getId(), UserTypeEnum.CUSTOMER.getValue(),
                        sendVO.getTemplateCode(),sendVO.getTemplateParams());
            }
        });

    }

    @Override
    public void sendMail(MailTemplateSendVO sendVO) {
        List<CrmCustomerContactsDO> crmCustomerDOS = null;
        if(sendVO.getIsCustomer()){
            crmCustomerDOS = customerContactsMapper.selectList(new LambdaQueryWrapper<CrmCustomerContactsDO>().
                    in(CrmCustomerContactsDO::getCustomerId,sendVO.getCustomerIds()));
        }else{
            crmCustomerDOS = customerContactsMapper.selectList(new LambdaQueryWrapper<CrmCustomerContactsDO>().
                    in(CrmCustomerContactsDO::getId,sendVO.getCustomerIds()));
        }
        if(crmCustomerDOS == null || crmCustomerDOS.isEmpty()){
            throw exception(new ErrorCode(202409101,"客户信息不存在"));
        }
        crmCustomerDOS.forEach(customer -> {
            //发送邮件
            if(StrUtil.isNotEmpty(customer.getEmail())){
                mailSendService.sendSingleMail(customer.getEmail(),customer.getId(), UserTypeEnum.CUSTOMER.getValue(),
                        sendVO.getTemplateCode(),sendVO.getTemplateParams());
            }
        });
    }

}