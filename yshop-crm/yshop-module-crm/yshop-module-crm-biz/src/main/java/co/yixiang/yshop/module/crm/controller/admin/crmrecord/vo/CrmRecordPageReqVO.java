package co.yixiang.yshop.module.crm.controller.admin.crmrecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 跟进记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmRecordPageReqVO extends PageParam {

    @Schema(description = "关联类型(customer跟进,business商机跟进,clues线索)")
    private String types;

    @Schema(description = "类型ID（根据types变动而变动）", example = "3187")
    private Long typesId;

    @Schema(description = "跟进内容")
    private String content;

    @Schema(description = "跟进类型/方式", example = "2")
    private Integer recordType;

    @Schema(description = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] nextTime;

    @Schema(description = "关系搜索")
    private String relation;

}