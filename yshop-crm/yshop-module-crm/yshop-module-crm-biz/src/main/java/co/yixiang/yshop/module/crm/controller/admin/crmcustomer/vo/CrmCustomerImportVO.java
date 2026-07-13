package co.yixiang.yshop.module.crm.controller.admin.crmcustomer.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class CrmCustomerImportVO {

    @ExcelProperty("客户名称")
    private String name;

    @ExcelProperty("手机")
    private String mobile;

    @ExcelProperty("电话")
    private String telephone;

    @ExcelProperty("客户标签")
    private String tags;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("详细地址")
    private String detailAddress;

    @ExcelProperty("地理位置经度")
    private Double lng;

    @ExcelProperty("地理位置维度")
    private Double lat;

    @ExcelProperty("微信")
    private String weixin;

    @ExcelProperty("QQ")
    private String qq;

    @ExcelProperty("消费总额")
    private BigDecimal purchaseTotal;


}