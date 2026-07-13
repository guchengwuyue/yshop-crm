package co.yixiang.yshop.module.crm.controller.admin.crmindex.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BrieCountVO {

    @Schema(description = "新增商机")
    private Long count01;

    @Schema(description = "昨日新增商机")
    private Long count001;

    @Schema(description = "较昨日比率")
    private String per001;

    @Schema(description = "新增线索")
    private Long count02;

    @Schema(description = "昨日新增线索")
    private Long count002;

    @Schema(description = "较昨日比率")
    private String per002;

    @Schema(description = "新增客户")
    private Long count03;

    @Schema(description = "昨日新增客户")
    private Long count003;

    @Schema(description = "较昨日比率")
    private String per003;

    @Schema(description = "新增跟进")
    private Long count04;

    @Schema(description = "昨日新增跟进")
    private Long count004;

    @Schema(description = "较昨日比率")
    private String per004;

    @Schema(description = "新增合同")
    private Long count05;

    @Schema(description = "昨日新增合同")
    private Long count005;

    @Schema(description = "较昨日比率")
    private String per005;

    @Schema(description = "合同金额")
    private BigDecimal count06;

    @Schema(description = "昨日合同金额")
    private BigDecimal count006;

    @Schema(description = "较昨日比率")
    private String per006;

    @Schema(description = "回款金额")
    private BigDecimal count07;

    @Schema(description = "昨日回款金额")
    private BigDecimal count007;

    @Schema(description = "较昨日比率")
    private String per007;

    @Schema(description = "新增联系人")
    private Long count08;

    @Schema(description = "昨日新增联系人")
    private Long count008;

    @Schema(description = "较昨日比率")
    private String per008;

    @Schema(description = "客户成交量")
    private Long count09;

    @Schema(description = "客户未成交量")
    private Long count10;

    @Schema(description = "商机成交量")
    private Long count11;
    @Schema(description = "商机未成交量")
    private Long count12;

    @Schema(description = "通知数")
    private Long count13;



}