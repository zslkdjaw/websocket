package cn.zxh.controller;

import cn.zxh.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhanghao
 * @create 2023-10-17-16:29
 */
@RestController
public class WebSocketController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;
    /**
     * description:
     * <br>
     * 客户端根据@MessageMapping内的路径发送消息
     * <br>
     * 服务端根据路径响应@MessageMapping内的路径注解下的方法
     * <br>
     * 服务端转发订阅了@SendTo路径的客户端
     */
    //广播所有人
    @MessageMapping("/receive/all")
    @SendTo("/topic/public")
    public Message sendAll(@Payload Message message){
        return message;
    }
    //订阅订阅VipTopic主题的用户
    @MessageMapping("/receive/topic")
    @SendTo("/topic/VipTopic")
    public Message sendToVIPTopic(@Payload Message message){
        return message;
    }
    //一对一
    @MessageMapping("/receive/one")
    public void sendToOne(@Payload Message message){
        //转化路径并发送
        simpMessagingTemplate.convertAndSendToUser(message.getRecUserId(),"/user/receive/one",message);
    }
}
