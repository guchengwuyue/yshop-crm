package co.yixiang.yshop.module.crm.controller.admin.crmbusiness.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 商机 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmBusinessRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20239")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16784")
    @ExcelProperty("客户ID")
    private Long customerId;

    @Schema(description = "客户")
    private String customerName;

    @Schema(description = "销售阶段", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("销售阶段")
    private Integer status;

    @Schema(description = "阶段推进时间")
    @ExcelProperty("阶段推进时间")
    private LocalDateTime statusTime;

    @Schema(description = "0洽淡中，1成交2失败3无效", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("0洽淡中，1成交2失败3无效")
    private Integer isEnd;

    @Schema(description = "下次联系时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("下次联系时间")
    private LocalDateTime nextTime;

    @Schema(description = "商机名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("商机名称")
    private String name;

    @Schema(description = "商机金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("商机金额")
    private BigDecimal money;

    @Schema(description = "产品总金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "30725")
    @ExcelProperty("产品总金额")
    private BigDecimal totalPrice;

    @Schema(description = "预计成交日期")
    @ExcelProperty("预计成交日期")
    private LocalDateTime dealTime;

    @Schema(description = "整单折扣", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("整单折扣")
    private BigDecimal discountRate;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24317")
    @ExcelProperty("负责人ID")
    private Long ownerUserId;


    @Schema(description = "添加时间")
    @ExcelProperty("添加时间")
    private LocalDateTime createTime;

    @Schema(description = "负责人")
    private String ownUserName;

}