package co.yixiang.yshop.module.message.mq.consumer;


import co.yixiang.yshop.framework.mq.redis.core.stream.AbstractRedisStreamMessageListener;
import co.yixiang.yshop.module.message.enums.WechatTempateEnum;
import co.yixiang.yshop.module.message.mq.message.WeixinNoticeMessage;
import co.yixiang.yshop.module.message.supply.WeixinTemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 消息队列处理消息推送
 */
@Component
@Slf4j
public class WeixinNoticeConsumer extends AbstractRedisStreamMessageListener<WeixinNoticeMessage> {

    @Resource
    private WeixinTemplateService weixinTemplateService;



    @Override
    public void onMessage(WeixinNoticeMessage message) {
        log.info("[onMessage][消息内容({})]", message);
        //公众号
        if(WechatTempateEnum.TEMPLATES.getValue().equals(message.getType())) {
            if(WechatTempateEnum.CHECK_NOTICE.getValue().equals(message.getTempkey())){
                weixinTemplateService.checkNotice(message.getTitle(),message.getTime(),message.getUid());
            }else if(WechatTempateEnum.RECORD_NOTICE.getValue().equals(message.getTempkey())){
                weixinTemplateService.recordNotice(message.getTitle(),message.getTime(),message.getUid());
            }
        }else if(WechatTempateEnum.SUBSCRIBE.getValue().equals(message.getType())){
            //小程序

        }


    }
}
