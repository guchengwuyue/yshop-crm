package co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 联系人 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmCustomerContactsRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31089")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12738")
    @ExcelProperty("客户ID")
    private Long customerId;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("姓名")
    private String name;

    @Schema(description = "手机", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("手机")
    private String mobile;

    @Schema(description = "生日")
    @ExcelProperty("生日")
    private Date birthday;

    @Schema(description = "电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("电话")
    private String telephone;

    @Schema(description = "微信号")
    @ExcelProperty("微信号")
    private String wechat;

    @Schema(description = "电子邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("电子邮箱")
    private String email;

    @Schema(description = "是否关键决策人1是0不是-1未知", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否关键决策人1是0不是-1未知")
    private Integer decision;

    @Schema(description = "职务", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("职务")
    private String post;

    @Schema(description = "性别0女1男-1未知", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("性别0女1男-1未知")
    private Integer sex;

    @Schema(description = "地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("地址")
    private String detailAddress;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;


    @Schema(description = "负责人ID")
    private Long ownerUserId;

    @Schema(description = "负责人")
    private String ownerUserName;

    @Schema(description = "下次联系时间")
    @ExcelProperty("下次联系时间")
    private LocalDateTime nextTime;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}