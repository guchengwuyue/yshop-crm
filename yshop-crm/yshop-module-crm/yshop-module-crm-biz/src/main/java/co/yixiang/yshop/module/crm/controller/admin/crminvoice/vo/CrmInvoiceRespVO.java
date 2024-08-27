package co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 发票 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmInvoiceRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21126")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "合同ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20659")
    @ExcelProperty("合同ID")
    private Long contractId;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "合同名称")
    private String contractName;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26226")
    @ExcelProperty("客户ID")
    private Long customerId;

    @Schema(description = "客户")
    private String customerName;

    @Schema(description = "开票主体")
    @ExcelProperty("开票主体")
    private String invoiceBody;

    @Schema(description = "开票内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开票内容")
    private String content;

    @Schema(description = "发票抬头", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("发票抬头")
    private String invoiceName;

    @Schema(description = "抬头类型1企业2个人3事业单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("抬头类型1企业2个人3事业单位")
    private Integer invoiceIssue;

    @Schema(description = "1增普2增专", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("1增普2增专")
    private Integer invoiceType;

    @Schema(description = "统一社会信用代码")
    @ExcelProperty("统一社会信用代码")
    private String registerNo;

    @Schema(description = "开户行名称", example = "张三")
    @ExcelProperty("开户行名称")
    private String bankName;

    @Schema(description = "开户账号")
    @ExcelProperty("开户账号")
    private String bankNo;

    @Schema(description = "发票税前金额")
    @ExcelProperty("发票税前金额")
    private BigDecimal money;

    @Schema(description = "税率", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("税率")
    private Integer taxRate;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "接收发票邮箱")
    @ExcelProperty("接收发票邮箱")
    private String email;

    @Schema(description = "收件人", example = "王五")
    @ExcelProperty("收件人")
    private String userName;

    @Schema(description = "收件手机")
    @ExcelProperty("收件手机")
    private String userPhone;

    @Schema(description = "邮寄地址")
    @ExcelProperty("邮寄地址")
    private String userAddress;

    @Schema(description = "开票附件")
    @ExcelProperty("开票附件")
    private String files;

    @Schema(description = "开票人", example = "26128")
    @ExcelProperty("开票人")
    private Long invoiceAdminId;

    @Schema(description = "0待审核、1审核中、2审核通过、3审核未通过", example = "1")
    @ExcelProperty("0待审核、1审核中、2审核通过、3审核未通过")
    private Integer checkStatus;

    @Schema(description = "审核流程ID", example = "4050")
    @ExcelProperty("审核流程ID")
    private Long flowId;

    @Schema(description = "审核步骤ID", example = "17760")
    @ExcelProperty("审核步骤ID")
    private Long stepId;

    @Schema(description = "已经审批人IDs", example = "8953")
    @ExcelProperty("已经审批人IDs")
    private String checkAdminId;

    @Schema(description = "当前需要审批的人", example = "17417")
    @ExcelProperty("当前需要审批的人")
    private String flowAdminId;

    private List<Long> flowAdminId2;

    @Schema(description = "状态0未开票1开票中2已开票3开票失败", example = "2")
    @ExcelProperty("状态0未开票1开票中2已开票3开票失败")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否有审核权限")
    private Boolean isCheck;

}