package co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 回款新增/修改 Request VO")
@Data
public class CrmContractReceivablesSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17817")
    private Long id;

    @Schema(description = "回款编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "回款编号不能为空")
    private String number;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6350")
    @NotNull(message = "客户不能为空")
    private Long customerId;

    @Schema(description = "合同ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11901")
    @NotNull(message = "合同不能为空")
    private Long contractId;

    @Schema(description = "回款日期")
    private LocalDateTime returnTime;

    @Schema(description = "收款账户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6941")
    @NotNull(message = "收款账户不能为空")
    private Integer accountId;

    @Schema(description = "回款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "回款金额不能为空")
    private BigDecimal money;

    @Schema(description = "0待审核、1审核中、2审核通过、3审核未通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer checkStatus;

    @Schema(description = "审核流程ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6781")
    private Long flowId;

    @Schema(description = "审核步骤排序ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27028")
    private Long stepId;

    @Schema(description = "审批人IDs", requiredMode = Schema.RequiredMode.REQUIRED, example = "29065")
    private String checkAdminId;

    @Schema(description = "流程审批人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20164")
    @NotEmpty(message = "审批人不能为空")
    private List<Long> flowAdminId;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19291")
    private Long ownerUserId;

    @Schema(description = "合同签约人", example = "31730")
    private Long orderAdminId;

    @Schema(description = "在线支付状态1已付款0未付款", example = "1")
    private Integer payStatus;

    @Schema(description = "收款方式1默认2在线收款3续费", example = "2")
    private Integer payType;

}