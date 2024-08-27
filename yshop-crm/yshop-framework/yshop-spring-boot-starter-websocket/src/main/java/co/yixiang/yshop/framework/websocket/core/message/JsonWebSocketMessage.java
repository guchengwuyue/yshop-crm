package co.yixiang.yshop.framework.websocket.core.message;

import co.yixiang.yshop.framework.websocket.core.listener.WebSocketMessageListener;
import lombok.Data;

import java.io.Serializable;

/**
 * JSON 格式的 WebSocket 消息帧
 *
 * @author yshop
 */
@Data
public class JsonWebSocketMessage implements Serializable {

    /**
     * 消息类型
     *
     * 目的：用于分发到对应的 {@link WebSocketMessageListener} 实现类
     */
    private String type;
    /**
     * 消息内容
     *
     * 要求 JSON 对象
     */
    private String content;

}
