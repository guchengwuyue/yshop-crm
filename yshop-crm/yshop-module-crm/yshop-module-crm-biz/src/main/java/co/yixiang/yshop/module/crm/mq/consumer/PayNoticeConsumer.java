package co.yixiang.yshop.module.crm.mq.consumer;

import co.yixiang.yshop.framework.mq.redis.core.stream.AbstractRedisStreamMessageListener;
import co.yixiang.yshop.module.crm.service.crmpay.PayService;
import co.yixiang.yshop.module.pay.mq.message.PayNoticeMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 消息队列处理支付消息
 */
@Component
@Slf4j
public class PayNoticeConsumer extends AbstractRedisStreamMessageListener<PayNoticeMessage> {

    @Resource
    private PayService payService;

    @Override
    public void onMessage(PayNoticeMessage message) {
        log.info("[onMessage][支付消息内容({})]", message);
        payService.paySuccess(message.getOrderId(),message.getPayType());
    }
}
