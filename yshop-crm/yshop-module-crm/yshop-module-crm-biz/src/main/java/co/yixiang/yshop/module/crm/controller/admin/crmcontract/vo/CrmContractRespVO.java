package co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 合同 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmContractRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8569")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31410")
    @ExcelProperty("客户ID")
    private Long customerId;

    @Schema(description = "客户")
    private String customerName;

    @Schema(description = "商机ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26442")
    @ExcelProperty("商机ID")
    private Long businessId;

    @Schema(description = "商机名称")
    private String businessName;

    @Schema(description = "客户签约人（联系人ID）", requiredMode = Schema.RequiredMode.REQUIRED, example = "27795")
    @ExcelProperty("客户签约人（联系人ID）")
    private Long contactsId;

    @Schema(description = "客户来源ID", example = "15518")
    @ExcelProperty("客户来源ID")
    private Integer sourceId;

    @Schema(description = "合同名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("合同名称")
    private String name;

    @Schema(description = "合同编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("合同编号")
    private String number;

    @Schema(description = "下单时间")
    @ExcelProperty("下单时间")
    private LocalDateTime orderTime;

    @Schema(description = "合同金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("合同金额")
    private BigDecimal money;

    @Schema(description = "产品总金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "10934")
    @ExcelProperty("产品总金额")
    private BigDecimal totalPrice;

    @Schema(description = "已收到款项")
    @ExcelProperty("已收到款项")
    private BigDecimal returnMoney;

    @Schema(description = "整单折扣", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("整单折扣")
    private BigDecimal discountRate;

    @Schema(description = "0待审核、1审核中、2审核通过、3审核未通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("0待审核、1审核中、2审核通过、3审核未通过")
    private Integer checkStatus;

    @Schema(description = "是否有审核权限")
    private Boolean isCheck;

    @Schema(description = "审核流程ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24535")
    @ExcelProperty("审核流程ID")
    private Long flowId;

    @Schema(description = "审核步骤ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21540")
    @ExcelProperty("审核步骤ID")
    private Long stepId;

    @Schema(description = "已经审批人IDs", requiredMode = Schema.RequiredMode.REQUIRED, example = "13317")
    @ExcelProperty("已经审批人IDs")
    private String checkAdminId;

    @Schema(description = "当前需要审批的人", requiredMode = Schema.RequiredMode.REQUIRED, example = "27407")
    private List<Long> flowAdminId2;

    @Schema(description = "当前需要审批的人")
    private String flowAdminId;

    @Schema(description = "开始时间")
    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @Schema(description = "公司签约人", example = "19289")
    @ExcelProperty("公司签约人")
    private Long orderAdminId;

    @Schema(description = "公司签约人姓名", example = "19289")
    private String orderAdminName;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16199")
    @ExcelProperty("负责人ID")
    private Long ownerUserId;


    @Schema(description = "下次联系时间")
    @ExcelProperty("下次联系时间")
    private LocalDateTime nextTime;

    @Schema(description = "0合同过期未处理1已续签2不再合作")
    @ExcelProperty("0合同过期未处理1已续签2不再合作")
    private Integer expireHandle;

    @Schema(description = "已开票金额")
    @ExcelProperty("已开票金额")
    private BigDecimal invoiceMoney;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人姓名", example = "19289")
    private String creatorName;

}