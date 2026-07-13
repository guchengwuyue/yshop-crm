package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Schema(description = "管理后台 - 团队员工vo")
@Data
@Builder
public class TeamUserVO {
    @Schema(description = "员工ID")
    private String userId;

    @Schema(description = "头像")
    private String userAvatar;

    @Schema(description = "名称")
    private String nickname;

    @Schema(description = "1-负责人 2 -普通员工")
    private Integer role;

    @Schema(description = "0-只读1-读写")
    private Integer permission;

}
