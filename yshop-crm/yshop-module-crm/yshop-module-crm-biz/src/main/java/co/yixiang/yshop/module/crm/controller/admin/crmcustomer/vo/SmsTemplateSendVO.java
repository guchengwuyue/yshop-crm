package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 短信模板的发送 Request VO")
@Data
public class SmsTemplateSendVO {

    @Schema(description = "客户ID")
    @NotNull(message = "请选择客户")
    private List<Long> customerIds;

    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_01")
    @NotNull(message = "模板编码不能为空")
    private String templateCode;

    @Schema(description = "模板参数")
    private Map<String, Object> templateParams;

    @Schema(description = "是否发送客户")
    private Boolean isCustomer;

}
