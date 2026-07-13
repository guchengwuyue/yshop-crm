package co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 部门业绩信息 Response VO")
@Data
public class UserAchieveRespVO {

    @Schema(description = "用户编号", example = "1024")
    private Long id;

    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "yshop")
    private String nickname;


    @Schema(description = "业绩明细")
    private CrmAchievementRespVO achievementRespVO;

}
