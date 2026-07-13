package co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 客户量分析信息 Response VO")
@Data
public class UserVolumeVO {

    @Schema(description = "用户编号", example = "1024")
    private Long id;

    @Schema(description = "用户名称")
    private String nickname;

    @Schema(description = "客户增量")
    private Long customerCount;

    @Schema(description = "成交数量")
    private Long successCount;

    @Schema(description = "成交比率")
    private String successPer;



}
