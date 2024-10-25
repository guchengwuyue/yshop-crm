package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Schema(description = "管理后台 - 客户新增/修改 Request VO")
@Data
public class CrmCustomerSaveReqVO {

    @Schema(description = "是否转移记录 1-是 2-否")
    private Integer isTransfer;

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12015")
    private Long id;

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "客户名称不能为空")
    private String name;

    @Schema(description = "手机", requiredMode = Schema.RequiredMode.REQUIRED)
   // @NotEmpty(message = "手机不能为空")
    private String mobile;

    @Schema(description = "电话", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotEmpty(message = "电话不能为空")
    private String telephone;

    @Schema(description = "成交状态0未成交1成交", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
   // @NotNull(message = "成交状态0未成交1成交不能为空")
    private Integer dealStatus;

    @Schema(description = "成交时间")
    private LocalDateTime dealTime;

    @Schema(description = "1锁定")
    private Boolean isLock;

    @Schema(description = "客户级别ID", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "客户级别ID不能为空")
    private Integer level;

    @Schema(description = "客户行业ID", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "客户行业ID不能为空")
    private Integer industry;

    @Schema(description = " 客户标签")
    private String tags;

    @Schema(description = "客户来源ID", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "客户来源ID不能为空")
    private Integer source;

    @Schema(description = "备注", example = "你说的对")
    private String remark;


    @Schema(description = "省份id")
    private Integer province;

    @Schema(description = "城市ID")
    private Integer city;

    @Schema(description = "区域ID")
    private Integer area;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotEmpty(message = "详细地址不能为空")
    private String detailAddress;

    @Schema(description = "地理位置经度")
    private Double lng;

    @Schema(description = "地理位置维度")
    private Double lat;

    @Schema(description = "下次联系时间", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "下次联系时间不能为空")
    private LocalDateTime nextTime;

    @Schema(description = "最后跟进时间")
    private LocalDateTime followTime;

    @Schema(description = "领取时间")
    private LocalDateTime collectTime;

    @Schema(description = "微信")
    private String weixin;

    @Schema(description = "QQ")
    private String qq;

    @Schema(description = "消费总额")
    private BigDecimal purchaseTotal;

    @Schema(description = "消费次数")
    private Integer purchaseTimes;

    @Schema(description = "跟进状态", example = "1")
    private Integer followStatus;



}