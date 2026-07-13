package co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 素材分组更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialGroupUpdateReqVO extends MaterialGroupBaseVO {

    @Schema(description = "编号", required = true, example = "1995")
    @NotNull(message = "编号不能为空")
    private Long id;

}
