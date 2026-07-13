package co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 商机 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_business")
@KeySequence("yshop_crm_business_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmBusinessDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 销售阶段
     */
    private Integer status;
    /**
     * 阶段推进时间
     */
    private LocalDateTime statusTime;
    /**
     * 0洽淡中，1成交2失败3无效
     */
    private Integer isEnd;
    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;
    /**
     * 商机名称
     */
    private String name;
    /**
     * 商机金额
     */
    private BigDecimal money;
    /**
     * 产品总金额
     */
    private BigDecimal totalPrice;
    /**
     * 预计成交日期
     */
    private LocalDateTime dealTime;
    /**
     * 整单折扣
     */
    private BigDecimal discountRate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 负责人ID
     */
    private Long ownerUserId;


}