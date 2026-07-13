package co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 客户分析 Response VO")
@Data
@Builder
public class CustomerLevelVO {

    @Schema(description = "等级")
    private List<String> data;

    @Schema(description = "所有客户")
    private List<Long> allCustomer;

    @Schema(description = "成交客户")
    private List<Long> dealCustomer;



}
