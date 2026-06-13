package co.yixiang.yshop.module.shop.controller.admin.material.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 素材库 Excel VO
 *
 * @author yshop
 */
@Data
public class MaterialExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("类型1、图片；2、视频")
    private String type;

    @ExcelProperty("分组ID")
    private String groupId;

    @ExcelProperty("素材名")
    private String name;

    @ExcelProperty("素材链接")
    private String url;

}
