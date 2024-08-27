package co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 素材分组创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialGroupCreateReqVO extends MaterialGroupBaseVO {

}
