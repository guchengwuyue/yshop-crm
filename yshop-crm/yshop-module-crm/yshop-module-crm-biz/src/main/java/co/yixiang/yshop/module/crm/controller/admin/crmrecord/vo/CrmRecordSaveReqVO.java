package co.yixiang.yshop.module.crm.controller.admin.crmrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 跟进记录新增/修改 Request VO")
@Data
public class CrmRecordSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1075")
    private Long id;

    @Schema(description = "关联类型(customer跟进,business商机跟进,clues线索)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "类型不能为空")
    private String types;

    @Schema(description = "类型ID（根据types变动而变动）", requiredMode = Schema.RequiredMode.REQUIRED, example = "3187")
    @NotNull(message = "类型ID不能为空")
    private Long typesId;

    @Schema(description = "跟进内容")
    private String content;

    @Schema(description = "跟进类型/方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "跟进方式不能为空")
    private Integer recordType;

    @Schema(description = "下次联系时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime nextTime;

    @Schema(description = "经度")
    private Double lng;

    @Schema(description = "维度")
    private Double lat;

    @Schema(description = "跟进状态")
    private Integer followStatus;

}