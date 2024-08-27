package co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 发票新增/修改 Request VO")
@Data
public class CrmInvoiceSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21126")
    private Long id;

    @Schema(description = "合同ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20659")
    @NotNull(message = "合同不能为空")
    private Long contractId;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26226")
    private Long customerId;

    @Schema(description = "开票主体")
    @NotEmpty(message = "开票主体不能为空")
    private String invoiceBody;

    @Schema(description = "开票内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "发票抬头", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "发票抬头不能为空")
    private String invoiceName;

    @Schema(description = "抬头类型1企业2个人3事业单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer invoiceIssue;

    @Schema(description = "1增普2增专", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer invoiceType;

    @Schema(description = "统一社会信用代码")
    private String registerNo;

    @Schema(description = "开户行名称", example = "张三")
    private String bankName;

    @Schema(description = "开户账号")
    private String bankNo;

    @Schema(description = "发票税前金额")
    @NotNull(message = "开票金额不能为空")
    private BigDecimal money;

    @Schema(description = "税率", requiredMode = Schema.RequiredMode.REQUIRED)
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
    private List<Long> flowAdminId;

    @Schema(description = "状态0未开票1开票中2已开票3开票失败", example = "2")
    private Integer status;

}