package co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 合同分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmContractPageReqVO extends PageParam {

    @Schema(description = "客户ID", example = "26442")
    private Long customerId;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "商机ID", example = "26442")
    private Long businessId;

    @Schema(description = "客户签约人（联系人ID）", example = "27795")
    private Long contactsId;

    @Schema(description = "客户来源ID", example = "15518")
    private Integer sourceId;

    @Schema(description = "合同名称", example = "赵六")
    private String name;

    @Schema(description = "合同编号")
    private String number;

    @Schema(description = "下单时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] orderTime;

    @Schema(description = "合同金额")
    private BigDecimal money;

    @Schema(description = "产品总金额", example = "10934")
    private BigDecimal totalPrice;

    @Schema(description = "已收到款项")
    private BigDecimal returnMoney;

    @Schema(description = "整单折扣")
    private BigDecimal discountRate;

    @Schema(description = "0待审核、1审核中、2审核通过、3审核未通过", example = "1")
    private Integer checkStatus;

    @Schema(description = "审核流程ID", example = "24535")
    private Long flowId;

    @Schema(description = "审核步骤ID", example = "21540")
    private Long stepId;

    @Schema(description = "已经审批人IDs", example = "13317")
    private String checkAdminId;

    @Schema(description = "当前需要审批的人", example = "27407")
    private String flowAdminId;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;


    @Schema(description = "公司签约人")
    private String orderAdminName;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "负责人ID", example = "16199")
    private Long ownerUserId;

    @Schema(description = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] nextTime;

    @Schema(description = "0合同过期未处理1已续签2不再合作")
    private Integer expireHandle;

    @Schema(description = "已开票金额")
    private BigDecimal invoiceMoney;

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