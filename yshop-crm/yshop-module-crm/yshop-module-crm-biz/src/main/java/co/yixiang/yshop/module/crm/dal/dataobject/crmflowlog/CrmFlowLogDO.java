package co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 审核日志 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_flow_log")
@KeySequence("yshop_crm_flow_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmFlowLogDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 审批流程ID
     */
    private Long flowId;
    /**
     * 对象ID（如审批类型ID）
     */
    private Long typesId;
    /**
     * 审核人
     */
    private Long adminId;
    /**
     * 关联对象 contract-合同 receivables-回款 invoice-发票
     */
    private String types;
    /**
     * 审核人名称
     */
    private String nickname;
    /**
     * 审核意见
     */
    private String remark;
    /**
     * 1审核结束
     */
    private Integer isEnd;
    /**
     * 0审核未通过,1审核通过
     */
    private Integer status;

}