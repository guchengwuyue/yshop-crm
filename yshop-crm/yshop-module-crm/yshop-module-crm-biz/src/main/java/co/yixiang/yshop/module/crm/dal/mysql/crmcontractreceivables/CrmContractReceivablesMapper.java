package co.yixiang.yshop.module.crm.dal.mysql.crmcontractreceivables;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo.CrmContractReceivablesPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo.CrmContractReceivablesRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 回款 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmContractReceivablesMapper extends BaseMapperX<CrmContractReceivablesDO> {

    default PageResult<CrmContractReceivablesRespVO> selectPage2(CrmContractReceivablesPageReqVO reqVO, List<Long> ids) {

        Long loginAdminId = SecurityFrameworkUtils.getLoginUserId();
        return selectJoinPage(reqVO, CrmContractReceivablesRespVO.class, new MPJLambdaWrapper<CrmContractReceivablesDO>()
                .selectAll(CrmContractReceivablesDO.class)
                .selectAs(CrmCustomerDO::getName,CrmContractReceivablesRespVO::getCustomerName)
                .selectAs(CrmContractDO::getName, CrmContractReceivablesRespVO::getContractName)
                .selectAs(CrmContractDO::getNumber, CrmContractReceivablesRespVO::getContractNumber)
                .selectAs(AdminUserDO::getNickname, CrmContractReceivablesRespVO::getCreatorName)
                .leftJoin(CrmCustomerDO.class,CrmCustomerDO::getId,CrmContractReceivablesDO::getCustomerId)
                .leftJoin(CrmContractDO.class, CrmContractDO::getId, CrmContractReceivablesDO::getContractId)
                .leftJoin(AdminUserDO.class, AdminUserDO::getId, CrmContractReceivablesDO::getCreator)
                .likeIfExists(CrmCustomerDO::getName, reqVO.getCustomerName())
                .eqIfExists(CrmContractDO::getName, reqVO.getContractName())
                .eqIfExists(CrmContractReceivablesDO::getCheckStatus, reqVO.getCheckStatus())
                .eqIfExists(CrmContractReceivablesDO::getNumber, reqVO.getNumber())
                .eqIfExists(CrmContractReceivablesDO::getContractId, reqVO.getContractId())
                .in(!ids.isEmpty(), CrmContractReceivablesDO::getOwnerUserId,ids)
                .likeIfExists(AdminUserDO::getNickname, reqVO.getCreatorName())
                .apply(reqVO.getIsCheck(), "FIND_IN_SET ('" + loginAdminId + "',t.flow_admin_id)")
        );


    }

    default PageResult<CrmContractReceivablesDO> selectPage(CrmContractReceivablesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmContractReceivablesDO>()
                .eqIfPresent(CrmContractReceivablesDO::getNumber, reqVO.getNumber())
                .eqIfPresent(CrmContractReceivablesDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(CrmContractReceivablesDO::getContractId, reqVO.getContractId())
                .betweenIfPresent(CrmContractReceivablesDO::getReturnTime, reqVO.getReturnTime())
                .eqIfPresent(CrmContractReceivablesDO::getAccountId, reqVO.getAccountId())
                .eqIfPresent(CrmContractReceivablesDO::getMoney, reqVO.getMoney())
                .eqIfPresent(CrmContractReceivablesDO::getCheckStatus, reqVO.getCheckStatus())
                .eqIfPresent(CrmContractReceivablesDO::getFlowId, reqVO.getFlowId())
                .eqIfPresent(CrmContractReceivablesDO::getStepId, reqVO.getStepId())
                .eqIfPresent(CrmContractReceivablesDO::getCheckAdminId, reqVO.getCheckAdminId())
                .eqIfPresent(CrmContractReceivablesDO::getFlowAdminId, reqVO.getFlowAdminId())
                .eqIfPresent(CrmContractReceivablesDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CrmContractReceivablesDO::getOwnerUserId, reqVO.getOwnerUserId())
                .eqIfPresent(CrmContractReceivablesDO::getOrderAdminId, reqVO.getOrderAdminId())
                .eqIfPresent(CrmContractReceivablesDO::getPayStatus, reqVO.getPayStatus())
                .eqIfPresent(CrmContractReceivablesDO::getPayType, reqVO.getPayType())
                .betweenIfPresent(CrmContractReceivablesDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CrmContractReceivablesDO::getId));
    }

}