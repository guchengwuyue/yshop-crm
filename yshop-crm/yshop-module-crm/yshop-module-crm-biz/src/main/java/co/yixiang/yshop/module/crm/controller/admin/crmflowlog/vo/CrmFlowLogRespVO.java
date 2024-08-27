package co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 审核日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmFlowLogRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28396")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "审批流程ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9346")
    @ExcelProperty("审批流程ID")
    private Long flowId;

    @Schema(description = "对象ID（如审批类型ID）", requiredMode = Schema.RequiredMode.REQUIRED, example = "12336")
    @ExcelProperty("对象ID（如审批类型ID）")
    private Long typesId;

    @Schema(description = "审核人", requiredMode = Schema.RequiredMode.REQUIRED, example = "30258")
    @ExcelProperty("审核人")
    private Long adminId;

    @Schema(description = "关联对象 contract-合同 receivables-回款 invoice-发票", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("关联对象 contract-合同 receivables-回款 invoice-发票")
    private String types;

    @Schema(description = "审核人名称", example = "芋艿")
    @ExcelProperty("审核人名称")
    private String nickname;

    @Schema(description = "审核意见", example = "随便")
    @ExcelProperty("审核意见")
    private String remark;

    @Schema(description = "1审核结束", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("1审核结束")
    private Integer isEnd;

    @Schema(description = "0审核未通过,1审核通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("0审核未通过,1审核通过")
    private Integer status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}