package co.yixiang.yshop.module.crm.controller.admin.crmclues.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 线索新增/修改 Request VO")
@Data
public class CrmCluesSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11763")
    private Long id;

    @Schema(description = "线索", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "线索不能为空")
    private String name;

    @Schema(description = "手机", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;

    @Schema(description = "电话", requiredMode = Schema.RequiredMode.REQUIRED)
    private String telephone;

    @Schema(description = "状态1-无效线索，0未转客户,1转成客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "转成客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22736")
    private Long customerId;

    @Schema(description = "转客户时间")
    private LocalDateTime changeTime;

    @Schema(description = "客户级别ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "客户级别ID不能为空")
    private Integer level;

    @Schema(description = "客户行业ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer industry;

    @Schema(description = " 客户标签")
    private String tags;

    @Schema(description = "客户来源ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer source;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13269")
    private Long ownerUserId;

    @Schema(description = "省份id")
    private Integer province;

    @Schema(description = "城市ID")
    private Integer city;

    @Schema(description = "区域ID")
    private Integer area;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String detailAddress;

    @Schema(description = "下次联系时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime nextTime;

    @Schema(description = "最后跟进时间")
    private LocalDateTime followTime;

    @Schema(description = "领取时间")
    private LocalDateTime collectTime;

    @Schema(description = "跟进状态", example = "2")
    private Integer followStatus;

}