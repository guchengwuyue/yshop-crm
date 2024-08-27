package co.yixiang.yshop.module.crm.dal.dataobject.crmrecord;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 跟进记录 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_record")
@KeySequence("yshop_crm_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmRecordDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 关联类型(customer跟进,business商机跟进,clues线索)
     */
    private String types;
    /**
     * 类型ID（根据types变动而变动）
     */
    private Long typesId;
    /**
     * 跟进内容
     */
    private String content;
    /**
     * 跟进类型/方式
     */
    private Integer recordType;
    /**
     * 下次联系时间
     */
    private LocalDateTime nextTime;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 维度
     */
    private Double lat;

}