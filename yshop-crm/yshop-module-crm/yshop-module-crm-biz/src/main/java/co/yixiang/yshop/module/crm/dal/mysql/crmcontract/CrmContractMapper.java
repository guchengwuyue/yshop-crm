package co.yixiang.yshop.module.crm.dal.mysql.crmcontract;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo.AchievementPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo.ContractVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CrmContractPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CrmContractRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
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
                .likeIfExists(CrmContractDO::getNumber, reqVO.getNumber())
                .likeIfExists(CrmContractDO::getName,reqVO.getName())
                .eqIfExists(CrmContractDO::getCustomerId, reqVO.getCustomerId())
                .eqIfExists(CrmContractDO::getCheckStatus, reqVO.getCheckStatus())
                .eqIfExists(CrmContractDO::getExpireHandle, reqVO.getExpireHandle())
                .in(!ids.isEmpty(), CrmContractDO::getOwnerUserId,ids)
                .likeIfExists("t1",AdminUserDO::getNickname, reqVO.getOrderAdminName())
                .likeIfExists("t2",AdminUserDO::getNickname, reqVO.getCreatorName())
                .orderByDesc(CrmContractDO::getId)
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

//    default  List<ContractVO> selectContractTop(AchievementPageReqVO reqVO) {
//
//        return selectJoinList(ContractVO.class, new MPJLambdaWrapper<CrmContractDO>()
//                .select(CrmContractDO::getOrderAdminId)
//                .selectCount(CrmContractDO::getId,ContractVO::getCount)
//                .selectSum(CrmContractDO::getMoney,ContractVO::getMoney)
//                .selectAs(AdminUserDO::getNickname, ContractVO::getNickname)
//                .leftJoin(AdminUserDO.class, AdminUserDO::getId, CrmContractDO::getOrderAdminId)
//                //.between(reqVO.getCreateTime() != null && reqVO.getCreateTime().length > 0,CrmContractDO::getCreateTime,reqVO.getCreateTime()[0],reqVO.getCreateTime()[1])
//                .groupBy(CrmContractDO::getOrderAdminId)
//                .orderByDesc(CrmContractDO::getMoney)
//                .apply("ORDER BY any_value(money) desc")
//                .last("limit 10")
//        );
//
//
//    }

    @Select("select any_value(t.id) as id,COUNT(t.id) AS count,SUM(t.money) AS money,t1.nickname AS nickname  from yshop_crm_contract  t " +
            "LEFT JOIN system_users t1 ON (t1.id = t.order_admin_id) " +
            "where t.deleted=0 GROUP BY t.order_admin_id ORDER BY any_value(money) DESC limit 10")
    List<ContractVO> selectContractTop();


    @Update("update yshop_crm_contract set return_money = return_money + #{price} " +
            " where id=#{contractId}")
    int incReturnMoney(@Param("price") BigDecimal price, @Param("contractId") Long contractId);

}