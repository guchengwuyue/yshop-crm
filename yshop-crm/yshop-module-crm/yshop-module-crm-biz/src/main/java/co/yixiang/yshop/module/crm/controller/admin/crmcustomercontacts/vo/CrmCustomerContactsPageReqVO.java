package co.yixiang.yshop.module.crm.controller.admin.crmcustomercontacts.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static co.yixiang.yshop.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 联系人分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmCustomerContactsPageReqVO extends PageParam {

    @Schema(description = "客户ID", example = "12738")
    private Long customerId;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "姓名", example = "芋艿")
    private String name;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "电话")
    private String telephone;

    @Schema(description = "微信号")
    private String wechat;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "关系搜索")
    private String relation;

    @Schema(description = "职务")
    private String post;

    @Schema(description = "性别0女1男-1未知")
    private Integer sex;


    @Schema(description = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] nextTime;



}