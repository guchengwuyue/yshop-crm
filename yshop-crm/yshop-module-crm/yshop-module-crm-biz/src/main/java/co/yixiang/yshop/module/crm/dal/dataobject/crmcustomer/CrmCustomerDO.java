package co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 客户 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_customer")
@KeySequence("yshop_crm_customer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmCustomerDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 成交状态0未成交1成交
     */
    private Integer dealStatus;
    /**
     * 成交时间
     */
    private LocalDateTime dealTime;
    /**
     * 1锁定
     */
    private Integer isLock;
    /**
     * 客户级别ID
     */
    private Integer level;
    /**
     * 客户行业ID
     */
    private Integer industry;
    /**
     *  客户标签
     */
    private String tags;
    /**
     * 客户来源ID
     */
    private Integer source;
    /**
     * 备注
     */
    private String remark;

    /**
     * 负责人ID
     */
    private Long ownerUserId;

    /**
     * 省份id
     */
    private Integer province;
    /**
     * 城市ID
     */
    private Integer city;
    /**
     * 区域ID
     */
    private Integer area;
    /**
     * 详细地址
     */
    private String detailAddress;
    /**
     * 地理位置经度
     */
    private Double lng;
    /**
     * 地理位置维度
     */
    private Double lat;
    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;
    /**
     * 最后跟进时间
     */
    private LocalDateTime followTime;
    /**
     * 领取时间
     */
    private LocalDateTime collectTime;
    /**
     * 微信
     */
    private String weixin;
    /**
     * QQ
     */
    private String qq;
    /**
     * 消费总额
     */
    private BigDecimal purchaseTotal;
    /**
     * 消费次数
     */
    private Integer purchaseTimes;
    /**
     * 跟进状态
     */
    private Integer followStatus;

}