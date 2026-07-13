package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 团队vo")
@Data
@Builder
public class TeamVO {

    @Schema(description = "员工ID")
    private String userId;

    @Schema(description = "客户ID")
    private Long customerId;

    @Schema(description = "员工ID")
    private List<String> adminIds;

    @Schema(description = "0-只读1-读写")
    private Integer permission;

}
