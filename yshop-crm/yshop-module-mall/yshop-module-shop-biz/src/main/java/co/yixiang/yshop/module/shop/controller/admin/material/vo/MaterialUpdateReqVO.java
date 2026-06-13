package co.yixiang.yshop.module.shop.controller.admin.material.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 素材库更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialUpdateReqVO extends MaterialBaseVO {

    @Schema(description = "编号", required = true, example = "9920")
    @NotNull(message = "编号不能为空")
    private Long id;

}
