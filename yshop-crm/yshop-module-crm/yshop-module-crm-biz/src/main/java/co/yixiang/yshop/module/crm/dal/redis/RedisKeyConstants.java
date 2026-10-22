package co.yixiang.yshop.module.crm.dal.redis;

/**
 * CRM Redis Key 常量
 *
 * @author yshop
 */
public interface RedisKeyConstants {

    /**
     * 首页统计缓存
     * <p>
     * KEY 格式：crm_index_count:{userId}:{relation}:{day}
     * VALUE 数据类型：CrmIndexRespVO
     * <p>
     * 使用 TimeoutRedisCacheManager 自定义 TTL：#60s
     */
    String CRM_INDEX_COUNT = "crm_index_count#60s";

}
