package co.yixiang.yshop.module.crm.dal.mysql.crminvoice;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoicePageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoiceRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crminvoice.CrmInvoiceDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 发票 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmInvoiceMapper extends BaseMapperX<CrmInvoiceDO> {


    default PageResult<CrmInvoiceRespVO> selectPage2(CrmInvoicePageReqVO reqVO, List<Long> ids) {
        Long loginAdminId = SecurityFrameworkUtils.getLoginUserId();
        return selectJoinPage(reqVO, CrmInvoiceRespVO.class, new MPJLambdaWrapper<CrmInvoiceDO>()
                .selectAll(CrmInvoiceDO.class)
                .selectAs(CrmCustomerDO::getName,CrmInvoiceRespVO::getCustomerName)
                .selectAs(CrmContractDO::getName, CrmInvoiceRespVO::getContractName)
                .selectAs(CrmContractDO::getNumber, CrmInvoiceRespVO::getContractNumber)
                //.selectAs(AdminUserDO::getNickname, CrmInvoiceRespVO::getCreatorName)
                .leftJoin(CrmCustomerDO.class,CrmCustomerDO::getId,CrmInvoiceDO::getCustomerId)
                .leftJoin(CrmContractDO.class, CrmContractDO::getId, CrmInvoiceDO::getContractId)
                //.leftJoin(AdminUserDO.class, AdminUserDO::getId, CrmInvoiceDO::getCreator)
                .likeIfExists(CrmCustomerDO::getName, reqVO.getCustomerName())
                .eqIfExists(CrmContractDO::getName, reqVO.getContractName())
                .eqIfExists(CrmContractReceivablesDO::getContractId, reqVO.getContractId())
                .in(!ids.isEmpty(), CrmInvoiceDO::getCreator,ids)
                .apply(reqVO.getIsCheck(), "FIND_IN_SET ('" + loginAdminId + "',t.flow_admin_id)")
        );


    }


    default PageResult<CrmInvoiceDO> selectPage(CrmInvoicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmInvoiceDO>()
                .eqIfPresent(CrmInvoiceDO::getContractId, reqVO.getContractId())
                .eqIfPresent(CrmInvoiceDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(CrmInvoiceDO::getInvoiceBody, reqVO.getInvoiceBody())
                .eqIfPresent(CrmInvoiceDO::getContent, reqVO.getContent())
                .likeIfPresent(CrmInvoiceDO::getInvoiceName, reqVO.getInvoiceName())
                .eqIfPresent(CrmInvoiceDO::getInvoiceIssue, reqVO.getInvoiceIssue())
                .eqIfPresent(CrmInvoiceDO::getInvoiceType, reqVO.getInvoiceType())
                .eqIfPresent(CrmInvoiceDO::getRegisterNo, reqVO.getRegisterNo())
                .likeIfPresent(CrmInvoiceDO::getBankName, reqVO.getBankName())
                .eqIfPresent(CrmInvoiceDO::getBankNo, reqVO.getBankNo())
                .eqIfPresent(CrmInvoiceDO::getMoney, reqVO.getMoney())
                .eqIfPresent(CrmInvoiceDO::getTaxRate, reqVO.getTaxRate())
                .eqIfPresent(CrmInvoiceDO::getRemarks, reqVO.getRemarks())
                .eqIfPresent(CrmInvoiceDO::getEmail, reqVO.getEmail())
                .likeIfPresent(CrmInvoiceDO::getUserName, reqVO.getUserName())
                .eqIfPresent(CrmInvoiceDO::getUserPhone, reqVO.getUserPhone())
                .eqIfPresent(CrmInvoiceDO::getUserAddress, reqVO.getUserAddress())
                .eqIfPresent(CrmInvoiceDO::getFiles, reqVO.getFiles())
                .eqIfPresent(CrmInvoiceDO::getInvoiceAdminId, reqVO.getInvoiceAdminId())
                .eqIfPresent(CrmInvoiceDO::getCheckStatus, reqVO.getCheckStatus())
                .eqIfPresent(CrmInvoiceDO::getFlowId, reqVO.getFlowId())
                .eqIfPresent(CrmInvoiceDO::getStepId, reqVO.getStepId())
                .eqIfPresent(CrmInvoiceDO::getCheckAdminId, reqVO.getCheckAdminId())
                .eqIfPresent(CrmInvoiceDO::getFlowAdminId, reqVO.getFlowAdminId())
                .eqIfPresent(CrmInvoiceDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CrmInvoiceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CrmInvoiceDO::getId));
    }

}