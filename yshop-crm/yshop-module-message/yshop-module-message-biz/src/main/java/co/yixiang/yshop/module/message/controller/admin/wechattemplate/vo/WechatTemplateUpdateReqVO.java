package co.yixiang.yshop.module.message.controller.admin.wechattemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 微信模板更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WechatTemplateUpdateReqVO extends WechatTemplateBaseVO {

    @Schema(description = "模板id", required = true, example = "8445")
    @NotNull(message = "模板id不能为空")
    private Integer id;

}
