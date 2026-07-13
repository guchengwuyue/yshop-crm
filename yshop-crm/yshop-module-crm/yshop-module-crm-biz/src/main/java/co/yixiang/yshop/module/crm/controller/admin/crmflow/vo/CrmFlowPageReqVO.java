package co.yixiang.yshop.module.crm.controller.admin.crmflow.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 审批流程分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmFlowPageReqVO extends PageParam {

    @Schema(description = "审批流名称", example = "赵六")
    private String name;

    @Schema(description = "关联对象 contract-合同 receivables-回款 invoice-发票")
    private String types;


    @Schema(description = "流程说明", example = "你说的对")
    private String remark;

    @Schema(description = "状态 1启用，0禁用", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}