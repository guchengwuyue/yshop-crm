package co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 销售简报 Response VO")
@Data
public class SalesVO {

    @Schema(description = "时间")
    private String time;

    @Schema(description = "新增线索")
    private Long cluesCount;

    @Schema(description = "新增客户")
    private Long customerCount;

    @Schema(description = "新增商机")
    private Long businessCount;

    @Schema(description = "新增合同")
    private Long contractCount;

    @Schema(description = "新增回款")
    private Long receivablesCount;



}
