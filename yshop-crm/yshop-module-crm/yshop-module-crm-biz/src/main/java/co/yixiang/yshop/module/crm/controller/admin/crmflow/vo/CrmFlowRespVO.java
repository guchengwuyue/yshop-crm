package co.yixiang.yshop.module.crm.controller.admin.crmflow.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 审批流程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmFlowRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21989")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "审批流名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("审批流名称")
    private String name;

    @Schema(description = "关联对象 contract-合同 receivables-回款 invoice-发票", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("关联对象 contract-合同 receivables-回款 invoice-发票")
    private String types;


    @Schema(description = "流程说明", requiredMode = Schema.RequiredMode.REQUIRED, example = "你说的对")
    @ExcelProperty("流程说明")
    private String remark;

    @Schema(description = "状态 1启用，0禁用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态 1启用，0禁用")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}