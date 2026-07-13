package co.yixiang.yshop.module.message.controller.admin.wechattemplate.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 微信模板 Excel 导出 Request VO，参数和 WechatTemplatePageReqVO 是一致的")
@Data
public class WechatTemplateExportReqVO {

    @Schema(description = "模板编号")
    private String tempkey;

    @Schema(description = "模板名", example = "张三")
    private String name;

    @Schema(description = "回复内容")
    private String content;

    @Schema(description = "模板ID", example = "15656")
    private String tempid;

    @Schema(description = "添加时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "状态", example = "1")
    private Byte status;

    @Schema(description = "类型：template:模板消息 subscribe:订阅消息", example = "1")
    private String type;

}
