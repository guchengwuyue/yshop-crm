package co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 素材分组 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialGroupRespVO extends MaterialGroupBaseVO {

    @Schema(description = "编号", required = true, example = "1995")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
