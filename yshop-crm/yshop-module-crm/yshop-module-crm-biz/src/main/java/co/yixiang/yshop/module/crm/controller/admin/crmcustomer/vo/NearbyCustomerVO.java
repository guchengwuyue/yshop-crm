package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NearbyCustomerVO {
    @Schema(description = "经度")
    @NotNull(message = "经度参数有误")
    private Double lng;
    @Schema(description = "纬度")
    @NotNull(message = "纬度参数有误")
    private Double lat;
    @Schema(description = "关系搜索")
    private String relation;
    @Schema(description = "距离")
    private Integer distance;
    @Schema(description = "名称")
    private String name;

}
