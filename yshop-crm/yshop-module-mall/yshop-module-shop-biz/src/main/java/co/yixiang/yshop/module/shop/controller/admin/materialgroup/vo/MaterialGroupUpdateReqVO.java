package co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 素材分组更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialGroupUpdateReqVO extends MaterialGroupBaseVO {

    @Schema(description = "编号", required = true, example = "1995")
    @NotNull(message = "编号不能为空")
    private Long id;

}
