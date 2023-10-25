package cn.zxh.websocket.config;

import cn.zxh.websocket.interceptor.WebSocketChannelInterceptor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.Resource;

/**
 * @author zhanghao
 * @create 2023-10-17-14:37
 */
@EnableWebSocketMessageBroker
@Component
public class WebsocketConfigure implements WebSocketMessageBrokerConfigurer {
    @Resource
    private WebSocketChannelInterceptor channelInterceptor;
    //配置注册响应端点: ws://ip:port//endpoint/
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOrigins("*") .withSockJS();
        registry.addEndpoint("/websocket");
    }
    //配置触发响应的路径: /app/destinationPrefix
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置两个路径,分别对应点对点(user)和广播(topic)的消息模型
        registry.setApplicationDestinationPrefixes("/app") //进入服务的路径前缀
                .setUserDestinationPrefix("/user/") //设置带有/user/前缀的转发为点对点传输 需要带传参/app/user/{userId}
                .enableSimpleBroker("/user/","/topic/"); //接收消息的路径前缀
    }
    //配置消息进入通道的拦截器
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelInterceptor);
    }
}

