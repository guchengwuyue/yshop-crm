package co.yixiang.yshop.framework.websocket.core.sender.local;

import co.yixiang.yshop.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import co.yixiang.yshop.framework.websocket.core.sender.WebSocketMessageSender;
import co.yixiang.yshop.framework.websocket.core.session.WebSocketSessionManager;

/**
 * 本地的 {@link WebSocketMessageSender} 实现类
 *
 * 注意：仅仅适合单机场景！！！
 *
 * @author yshop
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
