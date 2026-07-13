package co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 发票分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmInvoicePageReqVO extends PageParam {

    @Schema(description = "合同ID", example = "20659")
    private Long contractId;

    @Schema(description = "合同", example = "11901")
    private String contractName;

    @Schema(description = "客户ID", example = "26226")
    private Long customerId;

    @Schema(description = "客户", example = "6350")
    private String customerName;

    @Schema(description = "开票主体")
    private String invoiceBody;

    @Schema(description = "开票内容")
    private String content;

    @Schema(description = "发票抬头", example = "李四")
    private String invoiceName;

    @Schema(description = "抬头类型1企业2个人3事业单位")
    private Integer invoiceIssue;

    @Schema(description = "1增普2增专", example = "1")
    private Integer invoiceType;

    @Schema(description = "统一社会信用代码")
    private String registerNo;

    @Schema(description = "开户行名称", example = "张三")
    private String bankName;

    @Schema(description = "开户账号")
    private String bankNo;

    @Schema(description = "发票税前金额")
    private BigDecimal money;

    @Schema(description = "税率")
    private Integer taxRate;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "接收发票邮箱")
    private String email;

    @Schema(description = "收件人", example = "王五")
    private String userName;

    @Schema(description = "收件手机")
    private String userPhone;

    @Schema(description = "邮寄地址")
    private String userAddress;

    @Schema(description = "开票附件")
    private String files;

    @Schema(description = "开票人", example = "26128")
    private Long invoiceAdminId;

    @Schema(description = "0待审核、1审核中、2审核通过、3审核未通过", example = "1")
    private Integer checkStatus;

    @Schema(description = "审核流程ID", example = "4050")
    private Long flowId;

    @Schema(description = "审核步骤ID", example = "17760")
    private Long stepId;

    @Schema(description = "已经审批人IDs", example = "8953")
    private String checkAdminId;

    @Schema(description = "当前需要审批的人", example = "17417")
    private String flowAdminId;

    @Schema(description = "状态0未开票1开票中2已开票3开票失败", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "关系搜索")
    private String relation;

    @Schema(description = "审核合同")
    private Boolean isCheck = false;

}