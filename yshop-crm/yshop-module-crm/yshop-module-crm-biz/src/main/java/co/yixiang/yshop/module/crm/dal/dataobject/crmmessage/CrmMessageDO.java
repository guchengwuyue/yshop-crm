package co.yixiang.yshop.module.crm.dal.dataobject.crmmessage;

import lombok.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 提醒消息 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_message")
@KeySequence("yshop_crm_message_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmMessageDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 关联x类型 examine-审核 customer-客户
     */
    private String relationType;
    /**
     * 关联ID
     */
    private Long relationId;
    /**
     * 接收人ID
     */
    private Long toUserId;
    /**
     * 发送人ID
     */
    private Long fromUserId;
    /**
     * 发送内容
     */
    private String content;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 阅读时间
     */
    private LocalDateTime readTime;
    /**
     * 0 未操作 1已操作 
     */
    private Integer status;

}