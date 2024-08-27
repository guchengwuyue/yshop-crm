package co.yixiang.yshop.module.crm.controller.admin.crmbusiness.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 商机分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmBusinessPageReqVO extends PageParam {

    @Schema(description = "客户ID", example = "16784")
    private Long customerId;

    @Schema(description = "客户")
    private String customerName;

    @Schema(description = "销售阶段", example = "1")
    private Integer status;

    @Schema(description = "阶段推进时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] statusTime;

    @Schema(description = "0洽淡中，1成交2失败3无效")
    private Integer isEnd;

    @Schema(description = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] nextTime;

    @Schema(description = "商机名称", example = "王五")
    private String name;

    @Schema(description = "商机金额")
    private BigDecimal money;

    @Schema(description = "产品总金额", example = "30725")
    private BigDecimal totalPrice;

    @Schema(description = "预计成交日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] dealTime;

    @Schema(description = "整单折扣")
    private BigDecimal discountRate;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "负责人ID", example = "24317")
    private Long ownerUserId;

    @Schema(description = "负责人")
    private String ownerUserName;


    @Schema(description = "添加时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "关系搜索")
    private String relation;

}