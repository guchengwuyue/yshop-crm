package co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 支付服务商配置创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MerchantDetailsCreateReqVO extends MerchantDetailsBaseVO {

}
