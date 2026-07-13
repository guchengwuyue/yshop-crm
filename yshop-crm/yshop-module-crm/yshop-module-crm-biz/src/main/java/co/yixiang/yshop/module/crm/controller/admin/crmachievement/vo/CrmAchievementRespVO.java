package co.yixiang.yshop.module.crm.controller.admin.crmachievement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 业绩目标 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmAchievementRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20637")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "1销售（目标）2回款（目标）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("1销售（目标）2回款（目标）")
    private Integer config;

    @Schema(description = "3部门2员工", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("3部门2员工")
    private Integer type;

    @Schema(description = "对象ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29701")
    @ExcelProperty("对象ID")
    private Long typeId;

    @Schema(description = "年", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("年")
    private Integer year;

    @Schema(description = "一月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("一月")
    private BigDecimal january;

    @Schema(description = "二月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("二月")
    private BigDecimal february;

    @Schema(description = "三月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("三月")
    private BigDecimal march;

    @Schema(description = "四月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("四月")
    private BigDecimal april;

    @Schema(description = "五月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("五月")
    private BigDecimal may;

    @Schema(description = "六月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("六月")
    private BigDecimal june;

    @Schema(description = "七月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("七月")
    private BigDecimal july;

    @Schema(description = "八月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("八月")
    private BigDecimal august;

    @Schema(description = "九月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("九月")
    private BigDecimal september;

    @Schema(description = "十月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("十月")
    private BigDecimal october;

    @Schema(description = "十一月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("十一月")
    private BigDecimal november;

    @Schema(description = "十二月", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("十二月")
    private BigDecimal december;

    @Schema(description = "年目标", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("年目标")
    private BigDecimal yeartarget;

    @Schema(description = "添加时间")
    @ExcelProperty("添加时间")
    private LocalDateTime createTime;

}