package co.yixiang.yshop.module.crm.controller.admin.crmclues.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 线索 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmCluesRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11763")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "线索", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("线索")
    private String name;

    @Schema(description = "手机", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("手机")
    private String mobile;

    @Schema(description = "电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("电话")
    private String telephone;

    @Schema(description = "状态1-无效线索，0未转客户,1转成客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态1-无效线索，0未转客户,1转成客户")
    private Integer status;

    @Schema(description = "转成客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22736")
    @ExcelProperty("转成客户id")
    private Long customerId;

    @Schema(description = "转客户时间")
    @ExcelProperty("转客户时间")
    private LocalDateTime changeTime;

    @Schema(description = "客户级别ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("客户级别ID")
    private Integer level;

    @Schema(description = "客户行业ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("客户行业ID")
    private Integer industry;

    @Schema(description = " 客户标签")
    @ExcelProperty(" 客户标签")
    private String tags;

    @Schema(description = "客户来源ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("客户来源ID")
    private Integer source;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13269")
    @ExcelProperty("负责人ID")
    private Long ownerUserId;

    @Schema(description = "省份id")
    @ExcelProperty("省份id")
    private Integer province;

    @Schema(description = "城市ID")
    @ExcelProperty("城市ID")
    private Integer city;

    @Schema(description = "区域ID")
    @ExcelProperty("区域ID")
    private Integer area;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("详细地址")
    private String detailAddress;

    @Schema(description = "下次联系时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("下次联系时间")
    private LocalDateTime nextTime;

    @Schema(description = "最后跟进时间")
    @ExcelProperty("最后跟进时间")
    private LocalDateTime followTime;

    @Schema(description = "领取时间")
    @ExcelProperty("领取时间")
    private LocalDateTime collectTime;

    @Schema(description = "跟进状态", example = "2")
    @ExcelProperty("跟进状态")
    private Integer followStatus;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "负责人")
    private String ownUserName;

    @Schema(description = "创建人")
    private String createName;

}