package co.yixiang.yshop.module.crm.dal.dataobject.crmclues;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 线索 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_clues")
@KeySequence("yshop_crm_clues_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmCluesDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 线索
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
     * 状态1-无效线索，0未转客户,1转成客户
     */
    private Integer status;
    /**
     * 转成客户id
     */
    private Long customerId;
    /**
     * 转客户时间
     */
    private LocalDateTime changeTime;
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
     * 跟进状态1|新客
2|待再次沟通
3|有意向
4|已加微信
5|转商机
0|无效
     */
    private Integer followStatus;

}