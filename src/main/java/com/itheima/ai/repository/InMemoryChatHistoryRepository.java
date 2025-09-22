package com.itheima.ai.repository;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryChatHistoryRepository implements ChatHistoryRepository {

    private final Map<String, List<String>> chatHistory = new HashMap<>();

    @Override
    public void save(String type, String chatId) {
        // 如果 type 不存在，则创建一个新的列表存放该业务类型的 chatId （用户第一次打开过该type时）
        if (!chatHistory.containsKey(type)) {
            chatHistory.put(type, new ArrayList<>());
        }
        // 获取该业务类型的 chatId 列表
        List<String> chatIds = chatHistory.get(type);

        if (chatIds.contains(chatId)) {
            return;
        }
        chatIds.add(chatId);
    }

    @Override
    public List<String> getChatIds(String type) {
        List<String> chatIds = chatHistory.get(type);
        // 如果该业务类型不存在，则返回空列表，防止空指针异常
        return chatIds == null ? new ArrayList<>() : chatIds;
    }
}
