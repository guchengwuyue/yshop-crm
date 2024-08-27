package co.yixiang.yshop.module.crm.controller.admin.crmbusiness.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessProductDO;

@Schema(description = "管理后台 - 商机新增/修改 Request VO")
@Data
public class CrmBusinessSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20239")
    private Long id;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16784")
    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @Schema(description = "销售阶段", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "阶段推进时间")
    private LocalDateTime statusTime;

    @Schema(description = "0洽淡中，1成交2失败3无效", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isEnd;

    @Schema(description = "下次联系时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime nextTime;

    @Schema(description = "商机名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "商机名称不能为空")
    private String name;

    @Schema(description = "商机金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal money;

    @Schema(description = "产品总金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "30725")
    private BigDecimal totalPrice;

    @Schema(description = "预计成交日期")
    private LocalDateTime dealTime;

    @Schema(description = "整单折扣", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal discountRate;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24317")
    private Long ownerUserId;


    @Schema(description = "商机产品关联列表")
    private List<CrmBusinessProductDO> businessProducts;

}