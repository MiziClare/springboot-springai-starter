package com.itheima.ai.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

/**
 * 聊天消息的展示对象（VO），用于前端显示
 * 用于封装聊天消息的数据结构（封装消息的角色role和content）
 * 根据 Message 的类型（USER/ASSISTANT）设置 role 字段
 * 提取消息文本内容到 content 字段
 */
@NoArgsConstructor
@Data
public class MessageVO {

    /** 消息角色：user 或 assistant */
    private String role;
    /** 消息内容 */
    private String content;

    /**
     * 根据 Message 对象构造 MessageVO
     * @param message SpringAi的消息对象
     */
    public MessageVO(Message message) {
        switch (message.getMessageType()) {
            case USER -> {
                this.role = "user";
                break;
            }
            case ASSISTANT -> {
                this.role = "assistant";
                break;
            }
            default -> {
                this.role = "";
                break;
            }
        }
        this.content = message.getText();
    }
}
