package co.yixiang.yshop.module.crm.dal.mysql.crmcontract;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CrmContractPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CrmContractRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合同 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmContractMapper extends BaseMapperX<CrmContractDO> {

    default PageResult<CrmContractRespVO> selectPage2(CrmContractPageReqVO reqVO,List<Long> ids) {
        Long loginAdminId = SecurityFrameworkUtils.getLoginUserId();

        return selectJoinPage(reqVO, CrmContractRespVO.class, new MPJLambdaWrapper<CrmContractDO>()
                .selectAll(CrmContractDO.class)
                .selectAs("t3",CrmCustomerDO::getName,CrmContractRespVO::getCustomerName)
                .selectAs("t1", AdminUserDO::getNickname, CrmContractRespVO::getOrderAdminName)
                .selectAs("t2",AdminUserDO::getNickname, CrmContractRespVO::getCreatorName)
                .leftJoin(CrmCustomerDO.class,"t3",CrmCustomerDO::getId,CrmContractDO::getCustomerId)
                .leftJoin(AdminUserDO.class, "t1",AdminUserDO::getId, CrmContractDO::getOrderAdminId)
                .leftJoin(AdminUserDO.class, "t2",AdminUserDO::getId, CrmContractDO::getCreator)
                .likeIfExists(CrmCustomerDO::getName, reqVO.getCustomerName())
                .likeIfExists(CrmContractDO::getName, reqVO.getName())
                .eqIfExists(CrmContractDO::getNumber, reqVO.getNumber())
                .eqIfExists(CrmContractDO::getCustomerId, reqVO.getCustomerId())
                .eqIfExists(CrmContractDO::getCheckStatus, reqVO.getCheckStatus())
                .eqIfExists(CrmContractDO::getExpireHandle, reqVO.getExpireHandle())
                .in(!ids.isEmpty(), CrmContractDO::getOwnerUserId,ids)
                .likeIfExists("t1",AdminUserDO::getNickname, reqVO.getOrderAdminName())
                .likeIfExists("t2",AdminUserDO::getNickname, reqVO.getCreatorName())
                .apply(reqVO.getIsCheck(), "FIND_IN_SET ('" + loginAdminId + "',flow_admin_id)")
        );


    }

    default PageResult<CrmContractDO> selectPage(CrmContractPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmContractDO>()
                .eqIfPresent(CrmContractDO::getBusinessId, reqVO.getBusinessId())
                .eqIfPresent(CrmContractDO::getContactsId, reqVO.getContactsId())
                .eqIfPresent(CrmContractDO::getSourceId, reqVO.getSourceId())
                .likeIfPresent(CrmContractDO::getName, reqVO.getName())
                .eqIfPresent(CrmContractDO::getNumber, reqVO.getNumber())
                .betweenIfPresent(CrmContractDO::getOrderTime, reqVO.getOrderTime())
                .eqIfPresent(CrmContractDO::getMoney, reqVO.getMoney())
                .eqIfPresent(CrmContractDO::getTotalPrice, reqVO.getTotalPrice())
                .eqIfPresent(CrmContractDO::getReturnMoney, reqVO.getReturnMoney())
                .eqIfPresent(CrmContractDO::getDiscountRate, reqVO.getDiscountRate())
                .eqIfPresent(CrmContractDO::getCheckStatus, reqVO.getCheckStatus())
                .eqIfPresent(CrmContractDO::getFlowId, reqVO.getFlowId())
                .eqIfPresent(CrmContractDO::getStepId, reqVO.getStepId())
                .eqIfPresent(CrmContractDO::getCheckAdminId, reqVO.getCheckAdminId())
                .eqIfPresent(CrmContractDO::getFlowAdminId, reqVO.getFlowAdminId())
                .betweenIfPresent(CrmContractDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(CrmContractDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(CrmContractDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CrmContractDO::getOwnerUserId, reqVO.getOwnerUserId())
                .betweenIfPresent(CrmContractDO::getNextTime, reqVO.getNextTime())
                .eqIfPresent(CrmContractDO::getExpireHandle, reqVO.getExpireHandle())
                .eqIfPresent(CrmContractDO::getInvoiceMoney, reqVO.getInvoiceMoney())
                .betweenIfPresent(CrmContractDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CrmContractDO::getId));
    }

}