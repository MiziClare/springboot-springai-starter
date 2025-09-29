package com.itheima.ai.controller;
import com.itheima.ai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class CustomerServiceController {

    private final ChatClient serviceChatClient;
    private final ChatHistoryRepository chatHistoryRepository;

    @RequestMapping(value = "/service", produces = "text/html;charset=UTF-8")
    public Flux<String> service(String prompt, String chatId) {

        // 1.保存前端发来的 chatId 到会话历史 chatHistoryRepository 中
        chatHistoryRepository.save("service", chatId);

        // 2. 构造请求模型，发送请求
        return serviceChatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId) ) // 在这次 AI 调用中，告诉 ChatMemory 这次对话的对话ID是 chatId
                .stream()
                .content();
    }
}
