package cn.zxh.websocket.entity;

import lombok.Data;

/**
 * @author zhanghao
 * @create 2023-10-17-16:31
 */
@Data
public class Message {
    private String sendUserId;
    private String recUserId;
    private String content;

}
