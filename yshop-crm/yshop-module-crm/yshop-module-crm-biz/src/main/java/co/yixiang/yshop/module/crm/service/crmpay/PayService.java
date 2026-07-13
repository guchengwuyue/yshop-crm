package co.yixiang.yshop.module.crm.service.crmpay;


import co.yixiang.yshop.module.crm.controller.admin.crmpay.param.AppPayParam;

import java.util.Map;

/**
 * 订单 Service 接口
 *
 * @author yshop
 */
public interface PayService  {

    /**
     * 第三方支付
     * @param param 订单
     * @param uid 用户id
     */
    String pay(Long uid, AppPayParam param);


    /**
     * 支付成功后操作
     * @param orderId 订单号
     * @param payType 支付方式
     */
    void paySuccess(String orderId,String payType);



}
