package co.yixiang.yshop.module.crm.dal.mysql.crmflow;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowStepDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 审批步骤 Mapper
 *
 * @author yshop
 */
@Mapper
public interface CrmFlowStepMapper extends BaseMapperX<CrmFlowStepDO> {

    default List<CrmFlowStepDO> selectListByFlowId(Long flowId) {
        return selectList(CrmFlowStepDO::getFlowId, flowId);
    }

    default int deleteByFlowId(Long flowId) {
        return delete(CrmFlowStepDO::getFlowId, flowId);
    }

}