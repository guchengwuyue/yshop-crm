package co.yixiang.yshop.module.crm.controller.admin.crmindex.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 首页统计 Response VO")
@Data
@Builder
public class CrmIndexRespVO {

    @Schema(description = "需要跟进客户数量")
    private Long followCustomerCount;

    @Schema(description = "需要跟进商机数量")
    private Long followBusinessCount;

    @Schema(description = "需要跟进线索数量")
    private Long followCluesCount;

    @Schema(description = "即将到期等合同")
    private Long contractDueCount;

    @Schema(description = "即将到期等客户")
    private Long customerDueCount;

    @Schema(description = "待回款的合同")
    private Long contractReturnCount;

    @Schema(description = "我的发票")
    private Long myInvoiceCount;

    @Schema(description = "商品总数")
    private Long productCount;

    @Schema(description = "待审核等合同")
    private Long contractCheckCount;

    @Schema(description = "待审核等回款")
    private Long receivablesCheckCount;

    @Schema(description = "待审核等发票")
    private Long invoiceCheckCount;



}