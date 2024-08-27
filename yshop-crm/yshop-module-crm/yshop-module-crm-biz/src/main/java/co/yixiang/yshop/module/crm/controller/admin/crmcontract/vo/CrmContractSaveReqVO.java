package co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractProductDO;

@Schema(description = "管理后台 - 合同新增/修改 Request VO")
@Data
public class CrmContractSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8569")
    private Long id;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31410")
    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @Schema(description = "商机ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26442")
    private Long businessId;

    @Schema(description = "客户签约人（联系人ID）", requiredMode = Schema.RequiredMode.REQUIRED, example = "27795")
    private Long contactsId;

    @Schema(description = "客户来源ID", example = "15518")
    private Integer sourceId;

    @Schema(description = "合同名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "合同名称不能为空")
    private String name;

    @Schema(description = "合同编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "合同编号不能为空")
    private String number;

    @Schema(description = "下单时间")
    private LocalDateTime orderTime;

    @Schema(description = "合同金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "合同金额不能为空")
    private BigDecimal money;

    @Schema(description = "产品总金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "10934")
    private BigDecimal totalPrice;

    @Schema(description = "已收到款项")
    private BigDecimal returnMoney;

    @Schema(description = "整单折扣", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal discountRate;

    @Schema(description = "0待审核、1审核中、2审核通过、3审核未通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer checkStatus;

    @Schema(description = "审核流程ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24535")
    private Long flowId;

    @Schema(description = "审核步骤ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21540")
    private Long stepId;

    @Schema(description = "已经审批人IDs", requiredMode = Schema.RequiredMode.REQUIRED, example = "13317")
    private String checkAdminId;

    @Schema(description = "当前需要审批的人", requiredMode = Schema.RequiredMode.REQUIRED, example = "27407")
    @NotEmpty(message = "当前需要审批的人不能为空")
    private List<Long> flowAdminId;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "公司签约人", example = "19289")
    private Long orderAdminId;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16199")
    private Long ownerUserId;

    @Schema(description = "下次联系时间")
    private LocalDateTime nextTime;

    @Schema(description = "0合同过期未处理1已续签2不再合作")
    private Integer expireHandle;

    @Schema(description = "已开票金额")
    private BigDecimal invoiceMoney;

    @Schema(description = "合同产品关系列表")
    private List<CrmContractProductDO> contractProducts;

}