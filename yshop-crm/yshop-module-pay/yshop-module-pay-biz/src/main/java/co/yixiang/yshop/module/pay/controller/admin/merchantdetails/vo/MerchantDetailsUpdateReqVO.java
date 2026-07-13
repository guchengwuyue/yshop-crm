package co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 支付服务商配置更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MerchantDetailsUpdateReqVO extends MerchantDetailsBaseVO {

    @Schema(description = "列表id", required = true, example = "17552")
    @NotNull(message = "列表id不能为空")
    private String detailsId;

}
