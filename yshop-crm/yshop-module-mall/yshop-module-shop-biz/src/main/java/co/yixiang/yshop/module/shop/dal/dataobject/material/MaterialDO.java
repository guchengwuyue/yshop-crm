package co.yixiang.yshop.module.shop.dal.dataobject.material;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 素材库 DO
 *
 * @author yshop
 */
@TableName("yshop_material")
@KeySequence("yshop_material_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 类型1、图片；2、视频
     */
    private String type;
    /**
     * 分组ID
     */
    private String groupId;
    /**
     * 素材名
     */
    private String name;
    /**
     * 素材链接
     */
    private String url;

}
