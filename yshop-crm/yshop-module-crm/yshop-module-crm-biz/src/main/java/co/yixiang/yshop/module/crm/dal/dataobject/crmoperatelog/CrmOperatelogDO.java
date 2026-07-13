package co.yixiang.yshop.module.crm.dal.dataobject.crmoperatelog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 操作日志 DO
 *
 * @author yshop
 */
@TableName("yshop_crm_operatelog")
@KeySequence("yshop_crm_operatelog_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmOperatelogDO extends BaseDO {

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
     * 合同ID
     */
    private Long contractId;
    /**
     * 回款ID
     */
    private Long receivableId;
    /**
     * 姓名
     */
    private String nickname;
    /**
     * 管理员账号
     */
    private String username;
    /**
     * 操作页面
     */
    private String url;
    /**
     * 日志标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * IP
     */
    private String ip;
    /**
     * User-Agent
     */
    private String useragent;

}