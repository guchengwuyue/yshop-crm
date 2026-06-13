package co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 素材分组 Excel VO
 *
 * @author yshop
 */
@Data
public class MaterialGroupExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("分组名")
    private String name;

}
