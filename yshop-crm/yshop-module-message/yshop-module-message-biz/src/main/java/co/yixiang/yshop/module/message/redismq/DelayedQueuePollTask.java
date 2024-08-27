package co.yixiang.yshop.module.message.redismq;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RedissonClient;

/**
 * 延迟队列轮询任务类
 * @author hupeng
 * @date 2024.6.25
 */
@Slf4j
public class DelayedQueuePollTask<T> implements Runnable{
    private final RedissonClient redissonClient;

    private final DelayedQueueListener<T> delayedQueueListener;

    public DelayedQueuePollTask(RedissonClient redissonClient, DelayedQueueListener<T> delayedQueueListener) {
        this.redissonClient = redissonClient;
        this.delayedQueueListener = delayedQueueListener;
    }

    @Override
    public void run() {
        String threadName = "delayed-queue-listener-" + delayedQueueListener.getClass().getSimpleName();
        Thread.currentThread().setName(threadName);
        if (!delayedQueueListener.isEnable()) {
            return;
        }
        RBlockingDeque<T> blockingDeque = redissonClient.getBlockingDeque(delayedQueueListener.delayedQueueKey());
        // 解决消息丢失问题，发送subscribe命令订阅redis队列
        redissonClient.getDelayedQueue(blockingDeque);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                T message = blockingDeque.take();
                delayedQueueListener.consume(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                delayedQueueListener.whenExceptionFinally();
            }
        }
    }

}
