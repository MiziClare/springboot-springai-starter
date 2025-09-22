package com.itheima.ai.controller;

import com.itheima.ai.entity.vo.MessageVO;
import com.itheima.ai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/history")
public class ChatHistoryController {

    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatMemory chatMemory;

    /**
     * 根据业务类型查询其下的所有会话ID列表
     * @param type 业务类型，如：chat,service,pdf
     * @return 会话ID列表
     */
    @GetMapping("/{type}")
    public List<String> getChatIds(@PathVariable("type") String type) {
        return chatHistoryRepository.getChatIds(type);
    }

    /**
     * 根据业务类型、chatId查询指定会话的历史。先接受前端发来的chatId。
     * @param type 业务类型，如：chat,service,pdf
     * @param chatId 会话ID
     * @return 指定会话的历史消息
     */
    @GetMapping("/{type}/{chatId}")
    public List<MessageVO> getChatHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {

        // 注：chatMemory.get(...) 是 Spring AI 提供的接口，可以取出某个会话（chatId）里的消息。
        List<Message> messages = chatMemory.get(chatId);

        // 如果 chatMemory 里根本没有这个 chatId 的记录，就返回一个空集合，避免报 NullPointerException。
        if (messages == null) {
            return List.of();
        }

        /*
          1.把消息列表变成一个 Stream 流（将一个集合转换成stream）
          2.对每个 Message 元素，调用 new MessageVO(message)（MessageVO::new 指向的是 MessageVO 类的构造函数）
            工作原理：.map() 会从流中取出每一个 Message 对象，然后把它作为参数传递给 MessageVO 的构造函数来创建一个新的 MessageVO 对象。
            隐含前提：MessageVO 类中必须有一个构造函数，其接受一个 Message 对象作为参数，返回一个新的 MessageVO 对象。
          3.把处理好的流重新收集成一个 List，返回给前端。
         */
        return messages.stream().map(MessageVO::new).toList();
    }

}
