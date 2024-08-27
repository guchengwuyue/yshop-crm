package co.yixiang.yshop.module.shop.controller.admin.material.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 素材库 Excel 导出 Request VO，参数和 MaterialPageReqVO 是一致的")
@Data
public class MaterialExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "类型1、图片；2、视频", example = "2")
    private String type;

    @Schema(description = "分组ID", example = "21579")
    private String groupId;

    @Schema(description = "素材名", example = "yshop")
    private String name;

    @Schema(description = "素材链接", example = "https://www.yixiang.co")
    private String url;

}
