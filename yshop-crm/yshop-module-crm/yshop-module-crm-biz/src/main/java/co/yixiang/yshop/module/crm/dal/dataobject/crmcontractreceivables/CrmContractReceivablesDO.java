package co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables;

import co.yixiang.yshop.framework.mybatis.core.type.LongListTypeHandler;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 回款 DO
 *
 * @author yshop
 */
@TableName(value = "yshop_crm_contract_receivables",autoResultMap = true)
@KeySequence("yshop_crm_contract_receivables_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmContractReceivablesDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 回款编号
     */
    private String number;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 合同ID
     */
    private Long contractId;
    /**
     * 回款日期
     */
    private LocalDateTime returnTime;
    /**
     * 收款账户ID
     */
    private Integer accountId;
    /**
     * 回款金额
     */
    private BigDecimal money;
    /**
     * 0待审核、1审核中、2审核通过、3审核未通过
     */
    private Integer checkStatus;
    /**
     * 审核流程ID
     */
    private Long flowId;
    /**
     * 审核步骤排序ID
     */
    private Long stepId;
    /**
     * 审批人IDs
     */
    private String checkAdminId;
    /**
     * 流程审批人ID
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> flowAdminId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 负责人ID
     */
    private Long ownerUserId;
    /**
     * 合同签约人
     */
    private Long orderAdminId;
    /**
     * 在线支付状态1已付款0未付款
     */
    private Integer payStatus;
    /**
     * 收款方式1默认2在线收款3续费
     */
    private Integer payType;

}