package co.yixiang.yshop.module.crm.controller.admin.crmclues.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Builder
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class CrmCluesImportVO {


    @ExcelProperty("线索")
    private String name;

    @ExcelProperty("手机")
    private String mobile;

    @ExcelProperty("电话")
    private String telephone;


    @ExcelProperty(" 客户标签")
    private String tags;

    @ExcelProperty("备注")
    private String remark;


    @ExcelProperty("详细地址")
    private String detailAddress;


}