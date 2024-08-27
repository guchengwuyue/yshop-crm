package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmCustomerPageReqVO extends PageParam {

    @Schema(description = "客户名称", example = "李四")
    private String name;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "电话")
    private String telephone;

    @Schema(description = "成交状态0未成交1成交", example = "1")
    private Integer dealStatus;


    @Schema(description = "1锁定")
    private Boolean isLock;

    @Schema(description = "客户级别ID")
    private Integer level;

    @Schema(description = "客户行业ID")
    private Integer industry;

    @Schema(description = " 客户标签")
    private String tags;

    @Schema(description = "客户来源ID")
    private Integer source;

    @Schema(description = "关系搜索")
    private String relation;


    @Schema(description = "省份id")
    private Integer province;

    @Schema(description = "城市ID")
    private Integer city;

    @Schema(description = "区域ID")
    private Integer area;


    @Schema(description = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] nextTime;

    @Schema(description = "最后跟进时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] followTime;

    @Schema(description = "领取时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] collectTime;

    @Schema(description = "微信")
    private String weixin;

    @Schema(description = "QQ")
    private String qq;


    @Schema(description = "跟进状态", example = "1")
    private String followStatus;

    @Schema(description = "类型", example = "open")
    private String type;

    @Schema(description = "负责人")
    private String ownUserName;

    @Schema(description = "创建人")
    private String createName;



}