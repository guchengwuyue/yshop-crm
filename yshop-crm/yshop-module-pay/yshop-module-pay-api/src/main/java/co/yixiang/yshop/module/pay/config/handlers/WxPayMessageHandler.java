package co.yixiang.yshop.module.pay.config.handlers;

import co.yixiang.yshop.module.pay.mq.producer.PayNoticeProducer;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.wx.bean.WxPayMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Map;

/**
 * 微信支付回调处理器
 * @author hupeng
 * @date 2023/7/15
 */
@Component
@Slf4j
public class WxPayMessageHandler implements PayMessageHandler<WxPayMessage, PayService> {

    @Resource
    private PayNoticeProducer payNoticeProducer;

    @Override
    public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, PayService payService) throws PayErrorException {

        log.info("======pay notice ========");

        //交易状态
        if ("SUCCESS".equals(payMessage.getPayMessage().get("result_code"))){
            String orderId = (String)payMessage.getPayMessage().get("out_trade_no");
            //消息队列处理
            payNoticeProducer.sendPayNoticeMessage(orderId,"weixin");
            return  payService.getPayOutMessage("SUCCESS", "OK");
        }

        return  payService.getPayOutMessage("FAIL", "失败");
    }
}
