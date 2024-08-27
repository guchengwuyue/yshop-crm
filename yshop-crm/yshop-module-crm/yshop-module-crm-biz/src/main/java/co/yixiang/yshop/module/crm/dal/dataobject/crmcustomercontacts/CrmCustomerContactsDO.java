package co.yixiang.yshop.module.crm.dal.dataobject.crmcustomercontacts;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 联系人 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_customer_contacts")
@KeySequence("yshop_crm_customer_contacts_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmCustomerContactsDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 是否关键决策人1是0不是-1未知
     */
    private Integer decision;
    /**
     * 职务
     */
    private String post;
    /**
     * 性别0女1男-1未知
     */
    private Integer sex;
    /**
     * 地址
     */
    private String detailAddress;
    /**
     * 备注
     */
    private String remark;

    /**
     * 负责人ID
     */
    private Long ownerUserId;
    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;

}