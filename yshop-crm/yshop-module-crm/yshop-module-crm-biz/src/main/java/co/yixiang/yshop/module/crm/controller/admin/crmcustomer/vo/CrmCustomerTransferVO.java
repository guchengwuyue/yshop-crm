package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Schema(description = "管理后台 - 客户转移 VO")
@Data
public class CrmCustomerTransferVO {

    @Schema(description = "客户ID")
    private List<Long> customerIds;

    @Schema(description = "接受员工ID")
    private List<Long> ownerAdminIds;

    @Schema(description = "分配方式")
    private Integer averageType;


}