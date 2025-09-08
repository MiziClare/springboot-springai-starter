package com.itheima.ai.config;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;

@Configuration
public class CommonConfiguration {

    // 配置客户端
    @Bean
    public ChatClient chatClient(DeepSeekChatModel model) {
        return ChatClient
                .builder(model)
                .build();
    }
}
