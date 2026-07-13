package co.yixiang.yshop.module.shop.controller.admin.material.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 素材库 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialRespVO extends MaterialBaseVO {

    @Schema(description = "编号", required = true, example = "9920")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
