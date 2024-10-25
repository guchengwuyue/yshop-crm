package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 客户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmCustomerRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12015")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("客户名称")
    private String name;

    @Schema(description = "手机", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("手机")
    private String mobile;

    @Schema(description = "电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("电话")
    private String telephone;

    @Schema(description = "成交状态0未成交1成交", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("成交状态0未成交1成交")
    private Integer dealStatus;

    @Schema(description = "成交时间")
    @ExcelProperty("成交时间")
    private LocalDateTime dealTime;

    @Schema(description = "1锁定")
    @ExcelProperty("1锁定")
    private Integer isLock;

    @Schema(description = "客户级别ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("客户级别ID")
    private Integer level;

    @Schema(description = "客户行业ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("客户行业ID")
    private Integer industry;

    @Schema(description = " 客户标签")
    @ExcelProperty(" 客户标签")
    private String tags;

    @Schema(description = "客户来源ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("客户来源ID")
    private Integer source;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;


    @Schema(description = "省份id")
    @ExcelProperty("省份id")
    private Integer province;

    @Schema(description = "城市ID")
    @ExcelProperty("城市ID")
    private Integer city;

    @Schema(description = "区域ID")
    @ExcelProperty("区域ID")
    private Integer area;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("详细地址")
    private String detailAddress;

    @Schema(description = "地理位置经度")
    @ExcelProperty("地理位置经度")
    private Double lng;

    @Schema(description = "地理位置维度")
    @ExcelProperty("地理位置维度")
    private Double lat;

    @Schema(description = "下次联系时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("下次联系时间")
    private LocalDateTime nextTime;

    @Schema(description = "最后跟进时间")
    @ExcelProperty("最后跟进时间")
    private LocalDateTime followTime;

    @Schema(description = "领取时间")
    @ExcelProperty("领取时间")
    private LocalDateTime collectTime;

    @Schema(description = "微信")
    @ExcelProperty("微信")
    private String weixin;

    @Schema(description = "QQ")
    @ExcelProperty("QQ")
    private String qq;

    @Schema(description = "消费总额")
    @ExcelProperty("消费总额")
    private BigDecimal purchaseTotal;

    @Schema(description = "消费次数")
    @ExcelProperty("消费次数")
    private Integer purchaseTimes;

    @Schema(description = "跟进状态", example = "1")
    @ExcelProperty("跟进状态")
    private Integer followStatus;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "负责人")
    private String ownUserName;

    @Schema(description = "负责人")
    private String createName;

}