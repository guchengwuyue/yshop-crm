package co.yixiang.yshop.module.message.mq.producer;

import co.yixiang.yshop.framework.mq.redis.core.RedisMQTemplate;
import co.yixiang.yshop.module.message.mq.message.WeixinNoticeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Slf4j
@Component
public class WeixinNoticeProducer {
    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 发送消息
     * @param tempkey 订单编号
     * @param type 类型
     */
    public void sendNoticeMessage( Long uid,String tempkey,String type,String orderId,String price,
                                     String time,String deliveryName,String deliveryId,
                                    Long id,Integer numberId,String productName,String shopName) {
        WeixinNoticeMessage weixinNoticeMessage = new WeixinNoticeMessage()
                .setTempkey(tempkey).setType(type).setOrderId(orderId).setUid(uid)
                .setPrice(price).setDeliveryId(deliveryId).setTime(time).setDeliveryName(deliveryName)
                .setId(id).setNumberId(numberId).setProductName(productName).setShopName(shopName);
        redisMQTemplate.send(weixinNoticeMessage);
    }
}
