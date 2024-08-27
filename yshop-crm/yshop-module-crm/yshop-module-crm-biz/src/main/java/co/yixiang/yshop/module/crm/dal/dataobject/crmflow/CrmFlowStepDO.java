package co.yixiang.yshop.module.crm.dal.dataobject.crmflow;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 审批步骤 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_flow_step")
@KeySequence("yshop_crm_flow_step_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmFlowStepDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 审批流程ID
     */
    private Long flowId;
    /**
     * 1上级管理员，2指定用户（任意一人）3-指定部门
     */
    private Integer type;
    /**
     * 审批人ID,
     */
    private Long adminIds;
    /**
     * 审批流程关系（1并2或）
     */
    private Integer relation;

}