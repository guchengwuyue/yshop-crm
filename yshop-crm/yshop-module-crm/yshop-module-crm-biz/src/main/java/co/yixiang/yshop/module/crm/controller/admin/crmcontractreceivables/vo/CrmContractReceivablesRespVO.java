package co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 回款 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmContractReceivablesRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17817")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "回款编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("回款编号")
    private String number;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6350")
    @ExcelProperty("客户ID")
    private Long customerId;

    @Schema(description = "客户")
    private String customerName;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "合同ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11901")
    @ExcelProperty("合同ID")
    private Long contractId;

    @Schema(description = "合同名称")
    private String contractName;

    @Schema(description = "回款日期")
    @ExcelProperty("回款日期")
    private LocalDateTime returnTime;

    @Schema(description = "收款账户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6941")
    @ExcelProperty("收款账户ID")
    private Integer accountId;

    @Schema(description = "回款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("回款金额")
    private BigDecimal money;

    @Schema(description = "0待审核、1审核中、2审核通过、3审核未通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("0待审核、1审核中、2审核通过、3审核未通过")
    private Integer checkStatus;

    @Schema(description = "审核流程ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6781")
    @ExcelProperty("审核流程ID")
    private Long flowId;

    @Schema(description = "审核步骤排序ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27028")
    @ExcelProperty("审核步骤排序ID")
    private Long stepId;

    @Schema(description = "审批人IDs", requiredMode = Schema.RequiredMode.REQUIRED, example = "29065")
    @ExcelProperty("审批人IDs")
    private String checkAdminId;

    @Schema(description = "流程审批人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20164")
    @ExcelProperty("流程审批人ID")
    private String flowAdminId;

    private List<Long> flowAdminId2;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19291")
    @ExcelProperty("负责人ID")
    private Long ownerUserId;

    @Schema(description = "合同签约人", example = "31730")
    @ExcelProperty("合同签约人")
    private Long orderAdminId;

    @Schema(description = "在线支付状态1已付款0未付款", example = "1")
    @ExcelProperty("在线支付状态1已付款0未付款")
    private Integer payStatus;

    @Schema(description = "收款方式1默认2在线收款3续费", example = "2")
    @ExcelProperty("收款方式1默认2在线收款3续费")
    private Integer payType;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人姓名", example = "19289")
    private String creatorName;

    @Schema(description = "是否有审核权限")
    private Boolean isCheck;

}