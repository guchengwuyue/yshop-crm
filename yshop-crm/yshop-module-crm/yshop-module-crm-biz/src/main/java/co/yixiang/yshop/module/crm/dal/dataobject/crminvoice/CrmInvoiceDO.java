package co.yixiang.yshop.module.crm.dal.dataobject.crminvoice;

import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;
import co.yixiang.yshop.framework.mybatis.core.type.LongListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 发票 DO
 *
 * @author yshop
 */
@TableName(value = "yshop_crm_invoice",autoResultMap = true)
@KeySequence("yshop_crm_invoice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmInvoiceDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 合同ID
     */
    private Long contractId;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 开票主体
     */
    private String invoiceBody;
    /**
     * 开票内容
     */
    private String content;
    /**
     * 发票抬头
     */
    private String invoiceName;
    /**
     * 抬头类型1企业2个人3事业单位
     */
    private Integer invoiceIssue;
    /**
     * 1增普2增专
     */
    private Integer invoiceType;
    /**
     * 统一社会信用代码
     */
    private String registerNo;
    /**
     * 开户行名称
     */
    private String bankName;
    /**
     * 开户账号
     */
    private String bankNo;
    /**
     * 发票税前金额
     */
    private BigDecimal money;
    /**
     * 税率
     */
    private Integer taxRate;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 接收发票邮箱
     */
    private String email;
    /**
     * 收件人
     */
    private String userName;
    /**
     * 收件手机
     */
    private String userPhone;
    /**
     * 邮寄地址
     */
    private String userAddress;
    /**
     * 开票附件
     */
    private String files;
    /**
     * 开票人
     */
    private Long invoiceAdminId;
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
     * 状态0未开票1开票中2已开票3开票失败
     */
    private Integer status;

}