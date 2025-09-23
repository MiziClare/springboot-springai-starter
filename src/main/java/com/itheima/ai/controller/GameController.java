package com.itheima.ai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class GameController {

    // 该游戏不需要会话历史存储功能，因此不注入 ChatHistoryRepository
    private final ChatClient gameChatClient;

    @RequestMapping(value = "/game", produces = "text/html;charset=UTF-8")
    public Flux<String> chat(String prompt, String chatId) {

        // 构造请求模型，发送请求
        return gameChatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId) ) // 在这次 AI 调用中，告诉 ChatMemory 这次对话的对话ID是 chatId
                .stream()
                .content();
    }
}
