package com.itheima.ai.config;
import com.itheima.ai.constants.SystemConstants;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;

@Configuration
public class CommonConfiguration {

    // 添加会话记忆：SpringAi已经实现了会话记忆存储的对象，创建object后交给下方的环绕增强MessageChatMemoryAdvisor即可实现会话消息记忆。
    // 但是SpringAi提供的ChatMemory无查询会话消息历史功能。
    // 不过ChatMemory接口提供了根据ID查会话消息历史的方法即get(chatId)，可以取出某个会话（chatId）里的全部消息。
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .build();
    }

    // Configure client for general chat
    @Bean
    public ChatClient chatClient(OpenAiChatModel model, ChatMemory chatMemory) {
        return ChatClient
                .builder(model) // 创建 ChatClient 工厂实例
                .defaultSystem("你是一个智能小助手，致力于帮助用户解答各种问题。")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build() // MessageChatMemoryAdvisor 的构造方法是 private，只能通过 builder(...) 来生成实例。用来拦截我们与ai的对话。
                ) // 配置日志 Advisor
                .build(); // 构建 ChatClient 实例
    }

    // 纯 Prompt 模式的聊天游戏
    @Bean
    public ChatClient gameChatClient(OpenAiChatModel model, ChatMemory chatMemory) {
        return ChatClient
                .builder(model) // 创建 ChatClient 工厂实例
                .defaultSystem(SystemConstants.GAME_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build() // MessageChatMemoryAdvisor 的构造方法是 private，只能通过 builder(...) 来生成实例。用来拦截我们与ai的对话。
                ) // 配置日志 Advisor
                .build(); // 构建 ChatClient 实例
    }
}
