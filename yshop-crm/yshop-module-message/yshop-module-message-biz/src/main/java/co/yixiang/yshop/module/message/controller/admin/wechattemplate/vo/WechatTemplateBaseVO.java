package co.yixiang.yshop.module.message.controller.admin.wechattemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
* 微信模板 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class WechatTemplateBaseVO {

    @Schema(description = "模板编号", required = true)
    @NotNull(message = "模板编号不能为空")
    private String tempkey;

    @Schema(description = "模板名", required = true, example = "张三")
    @NotNull(message = "模板名不能为空")
    private String name;

    @Schema(description = "回复内容", required = true)
    private String content;

    @Schema(description = "模板ID", example = "15656")
    private String tempid;

    @Schema(description = "状态", required = true, example = "1")
    @NotNull(message = "状态不能为空")
    private Byte status;

    @Schema(description = "类型：template:模板消息 subscribe:订阅消息", example = "1")
    private String type;

}
