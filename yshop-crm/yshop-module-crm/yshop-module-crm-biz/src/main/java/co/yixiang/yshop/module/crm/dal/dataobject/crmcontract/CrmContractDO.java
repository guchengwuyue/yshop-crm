package co.yixiang.yshop.module.crm.dal.dataobject.crmcontract;

import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;
import co.yixiang.yshop.framework.mybatis.core.type.LongListTypeHandler;
import co.yixiang.yshop.framework.mybatis.core.type.StringListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合同 DO
 *
 * @author yshop
 */
@TableName(value = "yshop_crm_contract",autoResultMap = true)
@KeySequence("yshop_crm_contract_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmContractDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 商机ID
     */
    private Long businessId;
    /**
     * 客户签约人（联系人ID）
     */
    private Long contactsId;
    /**
     * 客户来源ID
     */
    private Integer sourceId;
    /**
     * 合同名称
     */
    private String name;
    /**
     * 合同编号
     */
    private String number;
    /**
     * 下单时间
     */
    private LocalDateTime orderTime;
    /**
     * 合同金额
     */
    private BigDecimal money;
    /**
     * 产品总金额
     */
    private BigDecimal totalPrice;
    /**
     * 已收到款项
     */
    private BigDecimal returnMoney;
    /**
     * 整单折扣
     */
    private BigDecimal discountRate;
    /**
     * 0待审核、1审核中、2审核通过、3审核未通过
     */
    private Integer checkStatus;
    /**
     * 审核流程ID
     */
    private Long flowId;
    /**
     * 审核步骤ID
     */
    private Long stepId;
    /**
     * 已经审批人IDs
     */

    private String checkAdminId;
    /**
     * 当前需要审批的人
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> flowAdminId;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 公司签约人
     */
    private Long orderAdminId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 负责人ID
     */
    private Long ownerUserId;

    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;
    /**
     * 0合同过期未处理1已续签2不再合作
     */
    private Integer expireHandle;
    /**
     * 已开票金额
     */
    private BigDecimal invoiceMoney;

}