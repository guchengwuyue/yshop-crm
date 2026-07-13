package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Schema(description = "管理后台 - 客户资料回收 VO")
@Data
public class CrmCustomerRecoverVO {

    @Schema(description = "离职ID")
    private List<Long> leaveAdminIds;

    @Schema(description = "接受员工ID")
    private List<Long> ownerAdminIds;

    @Schema(description = "分配方式")
    private Integer averageType;


}