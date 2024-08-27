package co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 联系人新增/修改 Request VO")
@Data
public class CrmCustomerContactsSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31089")
    private Long id;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12738")
    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "姓名不能为空")
    private String name;

    @Schema(description = "手机", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "电话", requiredMode = Schema.RequiredMode.REQUIRED)
    private String telephone;

    @Schema(description = "微信号")
    private String wechat;

    @Schema(description = "电子邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "是否关键决策人1是0不是-1未知", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer decision;

    @Schema(description = "职务", requiredMode = Schema.RequiredMode.REQUIRED)
    private String post;

    @Schema(description = "性别0女1男-1未知", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sex;

    @Schema(description = "地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String detailAddress;

    @Schema(description = "备注", example = "你说的对")
    private String remark;


    @Schema(description = "下次联系时间")
    private LocalDateTime nextTime;

}