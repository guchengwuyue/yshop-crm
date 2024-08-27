package co.yixiang.yshop.module.message.controller.admin.wechattemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 微信模板 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WechatTemplateRespVO extends WechatTemplateBaseVO {

    @Schema(description = "模板id", required = true, example = "8445")
    private Integer id;

    @Schema(description = "添加时间", required = true)
    private LocalDateTime createTime;

}
