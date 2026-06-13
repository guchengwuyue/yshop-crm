package co.yixiang.yshop.module.pay.mq.producer;

import co.yixiang.yshop.framework.mq.redis.core.RedisMQTemplate;
import co.yixiang.yshop.module.pay.mq.message.PayNoticeMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayNoticeProducer {
    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 发送消息
     * @param orderId 订单编号
     */
    public void sendPayNoticeMessage(String orderId,String payType) {
        PayNoticeMessage payNoticeMessage = new PayNoticeMessage().setOrderId(orderId).setPayType(payType);
        redisMQTemplate.send(payNoticeMessage);
    }
}
