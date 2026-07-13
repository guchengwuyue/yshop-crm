package co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 部门业绩信息 Response VO")
@Data
public class DeptAchieveRespVO {

    @Schema(description = "部门编号", example = "1024")
    private Long id;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "yshop")
    private String name;

    @Schema(description = "父部门 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long parentId;

    @Schema(description = "业绩明细")
    private CrmAchievementRespVO achievementRespVO;

}
