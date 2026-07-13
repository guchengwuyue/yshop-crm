package co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 回款分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmContractReceivablesPageReqVO extends PageParam {

    @Schema(description = "回款编号")
    private String number;

    @Schema(description = "客户ID", example = "6350")
    private Long customerId;

    @Schema(description = "客户", example = "6350")
    private String customerName;

    @Schema(description = "合同ID", example = "11901")
    private Long contractId;

    @Schema(description = "合同", example = "11901")
    private String contractName;

    @Schema(description = "回款日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] returnTime;

    @Schema(description = "收款账户ID", example = "6941")
    private Integer accountId;

    @Schema(description = "回款金额")
    private BigDecimal money;

    @Schema(description = "0待审核、1审核中、2审核通过、3审核未通过", example = "1")
    private Integer checkStatus;

    @Schema(description = "审核流程ID", example = "6781")
    private Long flowId;

    @Schema(description = "审核步骤排序ID", example = "27028")
    private Long stepId;

    @Schema(description = "审批人IDs", example = "29065")
    private String checkAdminId;

    @Schema(description = "流程审批人ID", example = "20164")
    private String flowAdminId;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "负责人ID", example = "19291")
    private Long ownerUserId;

    @Schema(description = "合同签约人", example = "31730")
    private Long orderAdminId;

    @Schema(description = "在线支付状态1已付款0未付款", example = "1")
    private Integer payStatus;

    @Schema(description = "收款方式1默认2在线收款3续费", example = "2")
    private Integer payType;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "创建人")
    private String creatorName;

    @Schema(description = "关系搜索")
    private String relation;

    @Schema(description = "审核合同")
    private Boolean isCheck = false;

}