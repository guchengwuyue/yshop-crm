package co.yixiang.yshop.module.crm.dal.mysql.crmcustomercontacts;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomercontacts.CrmCustomerContactsDO;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts.vo.*;

/**
 * 联系人 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmCustomerContactsMapper extends BaseMapperX<CrmCustomerContactsDO> {

    default PageResult<CrmCustomerContactsRespVO> selectPage(CrmCustomerContactsPageReqVO reqVO,List<Long> ids) {

        return selectJoinPage(reqVO,CrmCustomerContactsRespVO.class,new MPJLambdaWrapper<CrmCustomerContactsDO>()
                .selectAll(CrmCustomerContactsDO.class)
                .selectAs(CrmCustomerDO::getName,CrmCustomerContactsRespVO::getCustomerName)
                .selectAs(AdminUserDO::getNickname,CrmCustomerContactsRespVO::getOwnerUserName)
                .leftJoin(CrmCustomerDO.class,CrmCustomerDO::getId,CrmCustomerContactsDO::getCustomerId)
                .leftJoin(AdminUserDO.class,AdminUserDO::getId,CrmCustomerContactsDO::getOwnerUserId)
                .eqIfExists(CrmCustomerContactsDO::getCustomerId,reqVO.getCustomerId())
                .eqIfExists(CrmCustomerDO::getName,reqVO.getCustomerName())
                .eqIfExists(CrmCustomerContactsDO::getMobile,reqVO.getMobile())
                .eqIfExists(CrmCustomerContactsDO::getTelephone,reqVO.getTelephone())
                .eqIfExists(CrmCustomerContactsDO::getWechat,reqVO.getWechat())
                .eqIfExists(CrmCustomerContactsDO::getEmail,reqVO.getEmail())
                .in(!ids.isEmpty(),CrmCustomerContactsDO::getOwnerUserId,ids)
        );

    }

}