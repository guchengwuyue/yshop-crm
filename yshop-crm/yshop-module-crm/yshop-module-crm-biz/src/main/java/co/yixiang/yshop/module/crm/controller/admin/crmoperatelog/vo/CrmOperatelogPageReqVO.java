package co.yixiang.yshop.module.crm.controller.admin.crmoperatelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 操作日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmOperatelogPageReqVO extends PageParam {

    @Schema(description = "客户ID", example = "17340")
    private Long customerId;

    @Schema(description = "合同ID", example = "21099")
    private Long contractId;

    @Schema(description = "回款ID", example = "10316")
    private Long receivableId;

    @Schema(description = "姓名", example = "赵六")
    private String nickname;

    @Schema(description = "管理员账号", example = "王五")
    private String username;

    @Schema(description = "操作页面", example = "https://www.yixiang.co")
    private String url;

    @Schema(description = "日志标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "User-Agent")
    private String useragent;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}