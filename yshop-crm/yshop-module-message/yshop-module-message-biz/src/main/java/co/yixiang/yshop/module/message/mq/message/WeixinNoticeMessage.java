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


    private String title;

    private String code;

    //用户
    private Long uid;

    //时间
    private String time;


    private String owner;

    private String customer;




    @Override
    public String getStreamKey() {
        return "weixin.msg.notice";
    }
}
