package co.yixiang.yshop.module.crm.controller.admin.crmanalysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 跟进分析信息 Response VO")
@Data
public class UserRecordVO {

    @Schema(description = "用户编号", example = "1024")
    private Long id;

    @Schema(description = "用户名称")
    private String nickname;

    @Schema(description = "数量")
    private Long totalCount;

    @Schema(description = "客户数量")
    private Long customerCount;

    @Schema(description = "商机数量")
    private Long businessCount;

    @Schema(description = "线索数量")
    private Long cluesCount;



}
