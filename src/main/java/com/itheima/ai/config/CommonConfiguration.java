package com.itheima.ai.config;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;

@Configuration
public class CommonConfiguration {

    // Configure client
    @Bean
    public ChatClient chatClient(DeepSeekChatModel model) {
        return ChatClient
                .builder(model) // 创建 ChatClient 工厂实例
                .defaultSystem("你是一个智能小助手，致力于帮助用户解答各种问题。")
                .defaultAdvisors(new SimpleLoggerAdvisor()) // 配置日志 Advisor
                .build(); // 构建 ChatClient 实例
    }
}
