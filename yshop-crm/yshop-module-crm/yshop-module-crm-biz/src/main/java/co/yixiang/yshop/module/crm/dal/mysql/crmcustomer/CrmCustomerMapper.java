package co.yixiang.yshop.module.crm.dal.mysql.crmcustomer;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.CrmCustomerPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.CrmCustomerRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
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

    default PageResult<CrmCustomerRespVO> selectPage2(CrmCustomerPageReqVO reqVO) {

        return selectJoinPage(reqVO, CrmCustomerRespVO.class, new MPJLambdaWrapper<CrmCustomerDO>()
                .selectAll(CrmCustomerDO.class)
                .selectAs("t1",AdminUserDO::getNickname, CrmCustomerRespVO::getOwnUserName)
                .selectAs("t2",AdminUserDO::getNickname, CrmCustomerRespVO::getCreateName)
                .leftJoin(AdminUserDO.class, "t1",AdminUserDO::getId, CrmCustomerDO::getOwnerUserId)
                .leftJoin(AdminUserDO.class, "t2",AdminUserDO::getId, CrmCustomerDO::getCreator)
                .eqIfExists(CrmCustomerDO::getName, reqVO.getName())
                .eqIfExists(CrmCustomerDO::getMobile, reqVO.getMobile())
                .eqIfExists(CrmCustomerDO::getTelephone, reqVO.getTelephone())
                .eqIfExists(CrmCustomerDO::getWeixin, reqVO.getWeixin())
                .likeIfExists("t1",AdminUserDO::getNickname, reqVO.getOwnUserName())
                .likeIfExists("t2",AdminUserDO::getNickname, reqVO.getCreateName())
        );


    }

}