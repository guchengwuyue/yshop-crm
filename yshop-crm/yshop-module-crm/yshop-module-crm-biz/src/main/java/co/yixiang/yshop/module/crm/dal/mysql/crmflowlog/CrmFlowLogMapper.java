package co.yixiang.yshop.module.crm.dal.mysql.crmflowlog;

import java.util.*;

import cn.hutool.core.collection.CollUtil;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.CrmCustomerRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crminvoice.CrmInvoiceDO;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.*;

/**
 * 审核日志 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmFlowLogMapper extends BaseMapperX<CrmFlowLogDO> {

    default PageResult<CrmFlowLogRespVO> selectPage2(CrmFlowLogPageReqVO reqVO) {
        List<Integer> statusList = new ArrayList<>();
        if(reqVO.getStatus() != null && reqVO.getStatus() == 1){
            //已经审核
            statusList = CollUtil.newArrayList(1, 2);
        }else{
            //待审核
            statusList = CollUtil.newArrayList(0);
        }
        return selectJoinPage(reqVO, CrmFlowLogRespVO.class, new MPJLambdaWrapper<CrmFlowLogDO>()
                .selectAll(CrmFlowLogDO.class)
                .selectAs("t1", AdminUserDO::getNickname, CrmFlowLogRespVO::getAdminName)
                .selectAs("t2",AdminUserDO::getNickname, CrmFlowLogRespVO::getOtherUser)
                .selectAs("t3", CrmContractDO::getName,CrmFlowLogRespVO::getOtherName)
                .selectAs("t3", CrmContractDO::getNumber,CrmFlowLogRespVO::getOtherNo)
                .leftJoin(CrmContractDO.class,"t3", CrmContractDO::getId, CrmFlowLogDO::getTypesId)
                .leftJoin(AdminUserDO.class, "t1",AdminUserDO::getId, CrmFlowLogDO::getAdminId)
                .leftJoin(AdminUserDO.class, "t2",AdminUserDO::getId, CrmContractDO::getOwnerUserId)
                .eq(CrmFlowLogDO::getAdminId, SecurityFrameworkUtils.getLoginUserId())
                .eq(CrmFlowLogDO::getTypes, TypesEnum.CONTRACT.getValue())
                .in(CrmFlowLogDO::getStatus, statusList)
                .orderByDesc(CrmCustomerDO::getId)
        );
    }


    default PageResult<CrmFlowLogRespVO> selectPage3(CrmFlowLogPageReqVO reqVO) {
        List<Integer> statusList = new ArrayList<>();
        if(reqVO.getStatus() != null && reqVO.getStatus() == 1){
            //已经审核
            statusList = CollUtil.newArrayList(1, 2);
        }else{
            //待审核
            statusList = CollUtil.newArrayList(0);
        }
        return selectJoinPage(reqVO, CrmFlowLogRespVO.class, new MPJLambdaWrapper<CrmFlowLogDO>()
                .selectAll(CrmFlowLogDO.class)
                .selectAs("t1", AdminUserDO::getNickname, CrmFlowLogRespVO::getAdminName)
                .selectAs("t2",AdminUserDO::getNickname, CrmFlowLogRespVO::getOtherUser)
                .selectAs("t3", CrmContractReceivablesDO::getNumber,CrmFlowLogRespVO::getOtherNo)
                .selectAs("t3", CrmContractReceivablesDO::getMoney,CrmFlowLogRespVO::getOtherName)
                .leftJoin(CrmContractReceivablesDO.class,"t3", CrmContractReceivablesDO::getId, CrmFlowLogDO::getTypesId)
                .leftJoin(AdminUserDO.class, "t1",AdminUserDO::getId, CrmFlowLogDO::getAdminId)
                .leftJoin(AdminUserDO.class, "t2",AdminUserDO::getId, CrmContractReceivablesDO::getOwnerUserId)
                .eq(CrmFlowLogDO::getAdminId, SecurityFrameworkUtils.getLoginUserId())
                .eq(CrmFlowLogDO::getTypes, TypesEnum.RECEIVABLES.getValue())
                .in(CrmFlowLogDO::getStatus, statusList)
                .orderByDesc(CrmCustomerDO::getId)
        );
    }


    default PageResult<CrmFlowLogRespVO> selectPage4(CrmFlowLogPageReqVO reqVO) {
        List<Integer> statusList = new ArrayList<>();
        if(reqVO.getStatus() != null && reqVO.getStatus() == 1){
            //已经审核
            statusList = CollUtil.newArrayList(1, 2);
        }else{
            //待审核
            statusList = CollUtil.newArrayList(0);
        }
        return selectJoinPage(reqVO, CrmFlowLogRespVO.class, new MPJLambdaWrapper<CrmFlowLogDO>()
                .selectAll(CrmFlowLogDO.class)
                .selectAs("t1", AdminUserDO::getNickname, CrmFlowLogRespVO::getAdminName)
                .selectAs("t2",AdminUserDO::getNickname, CrmFlowLogRespVO::getOtherUser)
                .selectAs("t3", CrmInvoiceDO::getInvoiceBody,CrmFlowLogRespVO::getOtherNo)
                .selectAs("t3", CrmInvoiceDO::getMoney,CrmFlowLogRespVO::getOtherName)
                .leftJoin(CrmInvoiceDO.class,"t3", CrmInvoiceDO::getId, CrmFlowLogDO::getTypesId)
                .leftJoin(AdminUserDO.class, "t1",AdminUserDO::getId, CrmFlowLogDO::getAdminId)
                .leftJoin(AdminUserDO.class, "t2",AdminUserDO::getId, CrmInvoiceDO::getCreator)
                .eq(CrmFlowLogDO::getAdminId, SecurityFrameworkUtils.getLoginUserId())
                .eq(CrmFlowLogDO::getTypes, TypesEnum.INVOICE.getValue())
                .in(CrmFlowLogDO::getStatus, statusList)
                .orderByDesc(CrmCustomerDO::getId)
        );
    }


    default PageResult<CrmFlowLogDO> selectPage(CrmFlowLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmFlowLogDO>()
                .eqIfPresent(CrmFlowLogDO::getFlowId, reqVO.getFlowId())
                .eqIfPresent(CrmFlowLogDO::getTypesId, reqVO.getTypesId())
                .eqIfPresent(CrmFlowLogDO::getAdminId, reqVO.getAdminId())
                .eqIfPresent(CrmFlowLogDO::getTypes, reqVO.getTypes())
                .likeIfPresent(CrmFlowLogDO::getNickname, reqVO.getNickname())
                .eqIfPresent(CrmFlowLogDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CrmFlowLogDO::getIsEnd, reqVO.getIsEnd())
                .eqIfPresent(CrmFlowLogDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CrmFlowLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CrmFlowLogDO::getId));
    }

}