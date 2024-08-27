package co.yixiang.yshop.module.crm.dal.dataobject.crmflow;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 审批流程 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_flow")
@KeySequence("yshop_crm_flow_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmFlowDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 审批流名称
     */
    private String name;
    /**
     * 关联对象 contract-合同 receivables-回款 invoice-发票
     */
    private String types;

    /**
     * 流程说明
     */
    private String remark;
    /**
     * 状态 1启用，0禁用
     */
    private Integer status;

}