package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 客户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class NearbyCustomerRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12015")
    private Long id;

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    private String name;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String detailAddress;

    @Schema(description = "地理位置经度")
    private Double lng;

    @Schema(description = "地理位置维度")
    private Double lat;

    @Schema(description = "下次联系时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime nextTime;

    @Schema(description = "最后跟进时间")
    private LocalDateTime followTime;

    @Schema(description = "领取时间")
    private LocalDateTime collectTime;

    @Schema(description = "跟进状态", example = "1")
    private Integer followStatus;

    @Schema(description = "跟进状态")
    private String followStatusStr;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "负责人")
    private Long ownerUserId;

    @Schema(description = "负责人")
    private String ownUserName;

    @Schema(description = "距离")
    private String distance;



}