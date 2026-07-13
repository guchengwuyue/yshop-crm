package co.yixiang.yshop.module.crm.dal.mysql.crmcustomer;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.CrmCustomerPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.CrmCustomerRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 客户 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmCustomerMapper extends BaseMapperX<CrmCustomerDO> {

    default PageResult<CrmCustomerDO> selectPage(CrmCustomerPageReqVO reqVO,List<Long> ids) {


        return selectPage(reqVO, new LambdaQueryWrapperX<CrmCustomerDO>()
                //.eq(CustomerTypesEnum.OPEN.getValue().equals(reqVO.getType()),)
                .inIfPresent(CrmCustomerDO::getOwnerUserId,ids)
                .likeIfPresent(CrmCustomerDO::getName, reqVO.getName())
                .eqIfPresent(CrmCustomerDO::getMobile, reqVO.getMobile())
                .eqIfPresent(CrmCustomerDO::getTelephone, reqVO.getTelephone())
                .eqIfPresent(CrmCustomerDO::getDealStatus, reqVO.getDealStatus())
                .eqIfPresent(CrmCustomerDO::getLevel, reqVO.getLevel())
                .eqIfPresent(CrmCustomerDO::getIndustry, reqVO.getIndustry())
                .eqIfPresent(CrmCustomerDO::getSource, reqVO.getSource())
                .eqIfPresent(CrmCustomerDO::getWeixin, reqVO.getWeixin())
                .eqIfPresent(CrmCustomerDO::getQq, reqVO.getQq())
                .eqIfPresent(CrmCustomerDO::getFollowStatus, reqVO.getFollowStatus())
                .orderByDesc(CrmCustomerDO::getId));
    }

    default PageResult<CrmCustomerRespVO> selectPage2(CrmCustomerPageReqVO reqVO,List<Long> ids) {
        Long adminId = SecurityFrameworkUtils.getLoginUserId();
        return selectJoinPage(reqVO, CrmCustomerRespVO.class, new MPJLambdaWrapper<CrmCustomerDO>()
                .selectAll(CrmCustomerDO.class)
                .selectAs("t1",AdminUserDO::getNickname, CrmCustomerRespVO::getOwnUserName)
                .selectAs("t1",AdminUserDO::getAvatar, CrmCustomerRespVO::getOwnUserAvatar)
                .selectAs("t2",AdminUserDO::getNickname, CrmCustomerRespVO::getCreateName)
                .leftJoin(AdminUserDO.class, "t1",AdminUserDO::getId, CrmCustomerDO::getOwnerUserId)
                .leftJoin(AdminUserDO.class, "t2",AdminUserDO::getId, CrmCustomerDO::getCreator)
                .likeIfExists(CrmCustomerDO::getName, reqVO.getName())
                .eqIfExists(CrmCustomerDO::getMobile, reqVO.getMobile())
                .eqIfExists(CrmCustomerDO::getTelephone, reqVO.getTelephone())
                .eqIfExists(CrmCustomerDO::getWeixin, reqVO.getWeixin())
                .eqIfExists(CrmCustomerDO::getQq, reqVO.getQq())
                .eqIfExists(CrmCustomerDO::getDealStatus,reqVO.getDealStatus())
                .eqIfExists(CrmCustomerDO::getLevel, reqVO.getLevel())
                .eqIfExists(CrmCustomerDO::getIndustry, reqVO.getIndustry())
                .eqIfExists(CrmCustomerDO::getSource, reqVO.getSource())
                .between(reqVO.getNextTime()[0] != null && reqVO.getNextTime()[1] != null
                        ,CrmCustomerDO::getNextTime, reqVO.getNextTime()[0],reqVO.getNextTime()[1])
//                .in(!ids.isEmpty(),CrmCustomerDO::getOwnerUserId,ids).or().apply(
//                "FIND_IN_SET ('" + adminId + "',admin_id)"))
                .and(!ids.isEmpty(),i->i.in(!ids.isEmpty(),CrmCustomerDO::getOwnerUserId,ids).or()
                        .apply(RelationEnum.ALL.getValue().equals(reqVO.getRelation()),
                                "FIND_IN_SET ('" + adminId + "',rw_user_id)")
                        .or()
                        .apply(RelationEnum.ALL.getValue().equals(reqVO.getRelation()),
                                "FIND_IN_SET ('" + adminId + "',ro_user_id)"))
                .likeIfExists("t1",AdminUserDO::getNickname, reqVO.getOwnUserName())
                .likeIfExists("t2",AdminUserDO::getNickname, reqVO.getCreateName())
                .orderByDesc(CrmCustomerDO::getId)
        );


    }

}