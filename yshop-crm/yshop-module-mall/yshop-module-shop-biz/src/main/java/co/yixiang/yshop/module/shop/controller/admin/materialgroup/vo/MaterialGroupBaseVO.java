package co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
* 素材分组 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class MaterialGroupBaseVO {

    @Schema(description = "分组名", required = true, example = "赵六")
    @NotNull(message = "分组名不能为空")
    private String name;

}
