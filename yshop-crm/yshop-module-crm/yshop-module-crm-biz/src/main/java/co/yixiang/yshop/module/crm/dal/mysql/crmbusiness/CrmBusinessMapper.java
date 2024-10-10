package co.yixiang.yshop.module.crm.dal.mysql.crmbusiness;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo.CrmCustomerRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import co.yixiang.yshop.module.crm.controller.admin.crmbusiness.vo.*;

/**
 * 商机 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmBusinessMapper extends BaseMapperX<CrmBusinessDO> {

    default PageResult<CrmBusinessRespVO> selectPage(CrmBusinessPageReqVO reqVO,List<Long> ids) {

        return selectJoinPage(reqVO, CrmBusinessRespVO.class, new MPJLambdaWrapper<CrmBusinessDO>()
                .selectAll(CrmBusinessDO.class)
                .selectAs(CrmCustomerDO::getName, CrmBusinessRespVO::getCustomerName)
                .selectAs(AdminUserDO::getNickname, CrmBusinessRespVO::getOwnUserName)
                .leftJoin(CrmCustomerDO.class,CrmCustomerDO::getId, CrmBusinessDO::getCustomerId)
                .leftJoin(AdminUserDO.class,AdminUserDO::getId, CrmBusinessDO::getOwnerUserId)
                .in(!ids.isEmpty(),CrmBusinessDO::getOwnerUserId,ids)
                .likeIfExists(CrmCustomerDO::getName, reqVO.getCustomerName())
                .likeIfExists(CrmBusinessDO::getName,reqVO.getName())
                .eqIfExists(CrmBusinessDO::getCustomerId, reqVO.getCustomerId())
                .likeIfExists(AdminUserDO::getNickname, reqVO.getOwnerUserName())
        );

    }

}