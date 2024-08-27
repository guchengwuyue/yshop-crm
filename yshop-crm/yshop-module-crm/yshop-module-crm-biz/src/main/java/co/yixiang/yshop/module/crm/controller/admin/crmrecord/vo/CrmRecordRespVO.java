package co.yixiang.yshop.module.crm.controller.admin.crmrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 跟进记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmRecordRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1075")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "关联类型(customer跟进,business商机跟进,clues线索)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("关联类型(customer跟进,business商机跟进,clues线索)")
    private String types;

    @Schema(description = "类型ID（根据types变动而变动）", requiredMode = Schema.RequiredMode.REQUIRED, example = "3187")
    @ExcelProperty("类型ID（根据types变动而变动）")
    private Long typesId;

    @Schema(description = "类型名称")
    private String typesName;

    @Schema(description = "跟进内容")
    @ExcelProperty("跟进内容")
    private String content;

    @Schema(description = "跟进类型/方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("跟进类型/方式")
    private Integer recordType;

    @Schema(description = "下次联系时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("下次联系时间")
    private LocalDateTime nextTime;

    @Schema(description = "经度")
    @ExcelProperty("经度")
    private Double lng;

    @Schema(description = "维度")
    @ExcelProperty("维度")
    private Double lat;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}