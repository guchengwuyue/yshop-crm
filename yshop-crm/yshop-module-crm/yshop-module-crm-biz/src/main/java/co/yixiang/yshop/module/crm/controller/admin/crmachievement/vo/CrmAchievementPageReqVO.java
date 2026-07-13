package co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业绩目标分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmAchievementPageReqVO extends PageParam {

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "1销售（目标）2回款（目标）")
    private Integer config;

    @Schema(description = "2部门3员工", example = "1")
    private Integer type;

    @Schema(description = "对象ID", example = "29701")
    private Long typeId;

    @Schema(description = "年")
    private Integer year;

    @Schema(description = "一月")
    private BigDecimal january;

    @Schema(description = "二月")
    private BigDecimal february;

    @Schema(description = "三月")
    private BigDecimal march;

    @Schema(description = "四月")
    private BigDecimal april;

    @Schema(description = "五月")
    private BigDecimal may;

    @Schema(description = "六月")
    private BigDecimal june;

    @Schema(description = "七月")
    private BigDecimal july;

    @Schema(description = "八月")
    private BigDecimal august;

    @Schema(description = "九月")
    private BigDecimal september;

    @Schema(description = "十月")
    private BigDecimal october;

    @Schema(description = "十一月")
    private BigDecimal november;

    @Schema(description = "十二月")
    private BigDecimal december;

    @Schema(description = "年目标")
    private BigDecimal yeartarget;

    @Schema(description = "添加时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}