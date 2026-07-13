package co.yixiang.yshop.module.crm.controller.admin.crmclues.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Schema(description = "管理后台 - 线索转移 VO")
@Data
public class CrmCluesTransferVO {

    @Schema(description = "线索ID")
    private List<Long> cluesIds;

    @Schema(description = "接受员工ID")
    private List<Long> ownerAdminIds;

    @Schema(description = "分配方式")
    private Integer averageType;


}