package cn.zxh.interceptor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author zhanghao
 * @create 2023-10-17-15:10
 * @description websocket发送消息经过channel ,在channel前设置校验(token)
 * <br>
 * websocket的消息内容遵循了stomp协议,分为消息头header和消息负载payload两个部分
 * <br>
 * 命令（Command）：命令部分标识消息的类型或操作。它是消息的第一个部分，通常是一个动词，指示消息的目的和操作。一些常见的STOMP命令包括：
 * CONNECT：用于建立连接。
 * SUBSCRIBE：用于订阅一个目的地（主题或队列）以接收消息。
 * UNSUBSCRIBE：用于取消订阅一个目的地。
 * <br>
 * 消息头（Headers）：消息头部分包含与消息相关的一组键值对（Header Key-Value Pairs）。这些键值对提供了与消息相关的元数据信息
 * <br>
 * 空行（Empty Line）：消息头之后通常会有一个空行，表示消息头部分的结束。
 *<br>
 * 消息体（Body）：消息体是消息的主要内容，包含了实际的消息数据
 *<br>
 */
@Component
@Slf4j
public class WebSocketChannelInterceptor implements ChannelInterceptor {
    private static final String WEBSOCKET_TOKEN = "websocket_token";
    //发送进入通道前
    @SneakyThrows
    @Override
    //todo 针对不同的消息行为进行处理
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("发送进入通道前");
        //获取stomp消息头
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        Assert.notNull(headerAccessor,"StompHeaderAccessor must not be null");
        //针对不同的行为进行针对拦截
        //连接
        if(StompCommand.CONNECT.equals(headerAccessor.getCommand())){
            //约定,给消息头存放token
            String token = headerAccessor.getFirstNativeHeader(WEBSOCKET_TOKEN);
            if (StringUtils.isEmpty(token)){
                throw new Exception("客户端没有传递websocket token 无法校验");
            }
            //todo websocket的token校验
        }
        return message;
    }
    //发送消息到达通道后
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        log.info("发送消息到达通道后");
    }
    //通道发送消息完毕时
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        log.info("通道发送消息完毕时");
    }
    //通道接收消息完毕时
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        log.info("通道接收消息完毕时");
    }
}
