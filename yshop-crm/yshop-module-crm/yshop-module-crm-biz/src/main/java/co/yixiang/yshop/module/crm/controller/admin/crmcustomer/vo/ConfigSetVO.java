package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 客户规则配置 ConfigSetVO")
@Data
@Builder
public class ConfigSetVO {

    private Integer customerNum;

    private Integer notRecordDay;

    private Integer notSuccessDay;

}
