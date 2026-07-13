package co.yixiang.yshop.module.crm.dal.dataobject.crmachievement;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 业绩目标 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_achievement")
@KeySequence("yshop_crm_achievement_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmAchievementDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 1销售（目标）2回款（目标）
     */
    private Integer config;
    /**
     * 3部门2员工
     */
    private Integer type;
    /**
     * 对象ID
     */
    private Long typeId;
    /**
     * 年
     */
    private Integer year;
    /**
     * 一月
     */
    private BigDecimal january;
    /**
     * 二月
     */
    private BigDecimal february;
    /**
     * 三月
     */
    private BigDecimal march;
    /**
     * 四月
     */
    private BigDecimal april;
    /**
     * 五月
     */
    private BigDecimal may;
    /**
     * 六月
     */
    private BigDecimal june;
    /**
     * 七月
     */
    private BigDecimal july;
    /**
     * 八月
     */
    private BigDecimal august;
    /**
     * 九月
     */
    private BigDecimal september;
    /**
     * 十月
     */
    private BigDecimal october;
    /**
     * 十一月
     */
    private BigDecimal november;
    /**
     * 十二月
     */
    private BigDecimal december;
    /**
     * 年目标
     */
    private BigDecimal yeartarget;

}