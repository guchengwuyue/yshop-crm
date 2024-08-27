package co.yixiang.yshop.module.message.dal.dataobject.wechattemplate;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 微信模板 DO
 *
 * @author yshop
 */
@TableName("yshop_wechat_template")
@KeySequence("yshop_wechat_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatTemplateDO extends BaseDO {

    /**
     * 模板id
     */
    @TableId
    private Integer id;
    /**
     * 模板编号
     */
    private String tempkey;
    /**
     * 模板名
     */
    private String name;
    /**
     * 回复内容
     */
    private String content;
    /**
     * 模板ID
     */
    private String tempid;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 类型：template:模板消息 subscribe:订阅消息
     */
    private String type;

}
