
package co.yixiang.yshop.module.pay.config.interceptor;

import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayMessage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.common.exception.PayErrorException;

import java.util.Map;

/**
 * 支付宝回调信息拦截器
 * @author hupeng
 * @date 2023/7/15
 */
//使用 自己开启
//@Component
public class AliPayMessageInterceptor implements PayMessageInterceptor<AliPayMessage, AliPayService> {

    /**
     * 拦截支付消息
     *
     * @param payMessage     支付回调消息
     * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param payService 支付服务
     * @return true代表OK，false代表不OK并直接中断对应的支付处理器
     * @see PayMessageHandler 支付处理器
     * @throws PayErrorException PayErrorException*
     */
    @Override
    public boolean intercept(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService) throws PayErrorException {

        //这里进行拦截器处理，自行实现
        String outTradeNo = payMessage.getOutTradeNo();
        // todo



        return true;
    }
}
