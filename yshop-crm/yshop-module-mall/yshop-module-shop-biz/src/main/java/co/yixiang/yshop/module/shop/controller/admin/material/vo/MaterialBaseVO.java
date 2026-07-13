package co.yixiang.yshop.module.shop.controller.admin.material.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
* 素材库 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class MaterialBaseVO {

    @Schema(description = "类型1、图片；2、视频", required = true, example = "2")
    @NotNull(message = "类型1、图片；2、视频不能为空")
    private String type;

    @Schema(description = "分组ID", example = "21579")
    private String groupId;

    @Schema(description = "素材名", required = true, example = "yshop")
    @NotNull(message = "素材名不能为空")
    private String name;

    @Schema(description = "素材链接", example = "https://www.yixiang.co")
    private String url;

}
