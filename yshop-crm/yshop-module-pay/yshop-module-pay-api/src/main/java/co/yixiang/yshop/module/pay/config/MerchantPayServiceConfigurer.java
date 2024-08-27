package co.yixiang.yshop.module.pay.config;

import co.yixiang.yshop.module.pay.config.handlers.AliPayMessageHandler;
import co.yixiang.yshop.module.pay.config.handlers.WxPayMessageHandler;
import com.egzosn.pay.spring.boot.core.PayServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.MerchantDetailsServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.AliPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxPaymentPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.annotation.Resource;


/**
 * 支付服务配置
 *
 * @author hupeng
 * @date 2023/7/15
 */
@Configuration
public class MerchantPayServiceConfigurer implements PayServiceConfigurer {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private AliPayMessageHandler aliPayMessageHandler;
    @Resource
    private WxPayMessageHandler wxPayMessageHandler;

    /**
     * 商户配置
     *
     * @param merchants 商户配置
     */
    @Override
    public void configure(MerchantDetailsServiceConfigurer merchants)  {
        merchants.jdbc()
                //是否开启缓存，默认不开启,这里开启缓存
                .cache(false)
                .template(jdbcTemplate);

    }
    /**
     * 商户配置
     *
     * @param configurer 支付消息配置
     */
    @Override
    public void configure(PayMessageConfigurer configurer) {
        PaymentPlatform aliPaymentPlatform = PaymentPlatforms.getPaymentPlatform(AliPaymentPlatform.PLATFORM_NAME);
        PaymentPlatform wxPaymentPlatform = PaymentPlatforms.getPaymentPlatform(WxPaymentPlatform.PLATFORM_NAME);
        configurer.addHandler(aliPaymentPlatform, aliPayMessageHandler);
        configurer.addHandler(wxPaymentPlatform, wxPayMessageHandler);
        //configurer.addInterceptor(wxPaymentPlatform, spring.getBean(AliPayMessageInterceptor.class));
    }
}
