package co.yixiang.yshop.module.message.redismq;

/**
 * 延时队列接口
 * @author hupeng
 * @date 2024.6.25
 */
public interface DelayedQueueListener<T> {
    /**
     * 是否启用
     *
     * @return boolean
     */
    default boolean isEnable() {
        return true;
    }

    /**
     * 队列键
     *
     * @return String
     */
    String delayedQueueKey();

    /**
     * 消费
     *
     * @param message Object
     * @throws Exception Exception
     */
    void consume(T message) throws Exception;

    /**
     * 发生异常时最终处理
     */
    default void whenExceptionFinally() {
    }

}
