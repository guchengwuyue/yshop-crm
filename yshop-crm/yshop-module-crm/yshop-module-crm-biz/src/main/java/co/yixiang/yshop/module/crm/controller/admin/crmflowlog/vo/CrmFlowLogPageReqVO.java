package co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 审核日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmFlowLogPageReqVO extends PageParam {

    @Schema(description = "审批流程ID", example = "9346")
    private Long flowId;

    @Schema(description = "对象ID（如审批类型ID）", example = "12336")
    private Long typesId;

    @Schema(description = "审核人", example = "30258")
    private Long adminId;

    @Schema(description = "关联对象 contract-合同 receivables-回款 invoice-发票")
    private String types;

    @Schema(description = "审核人名称", example = "芋艿")
    private String nickname;

    @Schema(description = "审核意见", example = "随便")
    private String remark;

    @Schema(description = "1审核结束")
    private Integer isEnd;

    @Schema(description = "0审核未通过,1审核通过", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}