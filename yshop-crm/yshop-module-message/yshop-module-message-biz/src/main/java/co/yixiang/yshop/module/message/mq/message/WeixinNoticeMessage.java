package co.yixiang.yshop.module.message.mq.message;


import co.yixiang.yshop.framework.mq.redis.core.stream.AbstractRedisStreamMessage;
import lombok.Data;

@Data
public class WeixinNoticeMessage extends AbstractRedisStreamMessage {

    /**
     * 模板编号
     */
    private String tempkey;

    //消息类型
    private String type;

    //订单好
    private String orderId;

    //价格
    private String price;

    //用户
    private Long uid;

    //时间
    private String time;

    // 快递公司
    private String deliveryName;

    // 快递单号
    private String deliveryId;

    //订单ID
    private Long id;

    //取餐号
    private Integer numberId;

    //产品名称
    private String productName;

    //门店名称
    private String shopName;



    @Override
    public String getStreamKey() {
        return "weixin.msg.notice";
    }
}
