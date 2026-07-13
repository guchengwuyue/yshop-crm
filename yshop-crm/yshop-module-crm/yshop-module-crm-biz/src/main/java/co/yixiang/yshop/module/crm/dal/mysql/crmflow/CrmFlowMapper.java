package co.yixiang.yshop.module.crm.dal.mysql.crmflow;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.module.crm.controller.admin.crmflow.vo.CrmFlowPageReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 审批流程 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmFlowMapper extends BaseMapperX<CrmFlowDO> {

    default PageResult<CrmFlowDO> selectPage(CrmFlowPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmFlowDO>()
                .likeIfPresent(CrmFlowDO::getName, reqVO.getName())
                .eqIfPresent(CrmFlowDO::getTypes, reqVO.getTypes())
                .eqIfPresent(CrmFlowDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CrmFlowDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CrmFlowDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CrmFlowDO::getId));
    }

}