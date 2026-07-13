package co.yixiang.yshop.module.message.controller.admin.wechattemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 微信模板 Excel VO
 *
 * @author yshop
 */
@Data
public class WechatTemplateExcelVO {

    @ExcelProperty("模板id")
    private Integer id;

    @ExcelProperty("模板编号")
    private String tempkey;

    @ExcelProperty("模板名")
    private String name;

    @ExcelProperty("回复内容")
    private String content;

    @ExcelProperty("模板ID")
    private String tempid;

    @ExcelProperty("添加时间")
    private LocalDateTime createTime;

    @ExcelProperty("状态")
    private Byte status;

    @ExcelProperty("类型：template:模板消息 subscribe:订阅消息")
    private String type;

}
