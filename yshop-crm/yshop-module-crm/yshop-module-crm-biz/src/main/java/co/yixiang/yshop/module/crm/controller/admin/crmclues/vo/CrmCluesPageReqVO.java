package co.yixiang.yshop.module.crm.controller.admin.crmclues.vo;

import co.yixiang.yshop.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 线索分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmCluesPageReqVO extends PageParam {

    @Schema(description = "线索", example = "李四")
    private String name;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "电话")
    private String telephone;

    @Schema(description = "关系搜索")
    private String relation;

    @Schema(description = "状态1-无效线索，0未转客户,1转成客户", example = "1")
    private Integer status;

    @Schema(description = "转成客户id", example = "22736")
    private Long customerId;

    @Schema(description = "转客户时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] changeTime;

    @Schema(description = "客户级别ID")
    private Integer level;

    @Schema(description = "客户行业ID")
    private Integer industry;

    @Schema(description = " 客户标签")
    private String tags;

    @Schema(description = "客户来源ID")
    private Integer source;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "负责人ID", example = "13269")
    private Long ownerUserId;

    @Schema(description = "负责人")
    private String ownerUserName;


    @Schema(description = "省份id")
    private Integer province;

    @Schema(description = "城市ID")
    private Integer city;

    @Schema(description = "区域ID")
    private Integer area;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] nextTime;

    @Schema(description = "最后跟进时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] followTime;

    @Schema(description = "领取时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] collectTime;

    @Schema(description = "跟进状态", example = "2")
    private Integer followStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "类型", example = "open")
    private String type;

}