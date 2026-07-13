package co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 审核信息 VO")
@Data
public class CheckInfoVO {

    @Schema(description = "id")
    @NotNull(message = "参数缺失")
    private Long id;



    @Schema(description = "1通过 2拒绝")
    @NotNull(message = "类型缺失")
    private Integer agreeType;

    @Schema(description = "拒绝理由")
    private String remark;


}