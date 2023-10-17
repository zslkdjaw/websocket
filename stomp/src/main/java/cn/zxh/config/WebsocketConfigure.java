package cn.zxh.config;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author zhanghao
 * @create 2023-10-17-14:37
 */
@EnableWebSocketMessageBroker
public class WebsocketConfigure implements WebSocketMessageBrokerConfigurer {
    //注册端点:
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

    }
    //服务端点:指定端点对应指定的消息模型

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

    }
}
