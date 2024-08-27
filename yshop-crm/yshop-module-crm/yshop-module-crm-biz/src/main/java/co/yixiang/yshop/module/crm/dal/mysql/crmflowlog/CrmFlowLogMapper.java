package co.yixiang.yshop.module.crm.dal.mysql.crmflowlog;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import org.apache.ibatis.annotations.Mapper;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.*;

/**
 * 审核日志 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmFlowLogMapper extends BaseMapperX<CrmFlowLogDO> {

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