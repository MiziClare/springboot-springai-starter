package com.itheima.ai.repository;

import java.util.List;

public interface ChatHistoryRepository {

    /**
     * 保存会话记录，记录存储所有的会话ID（基于业务类型type）
     * @param type 业务类型，如：chat、service, pdf等
     * @param chatId 会话ID
     */
    void save(String type, String chatId);

    /**
     * 获取该type下所有的会话ID列表
     * @param type 业务类型，如：chat、service, pdf等
     * @return 会话ID列表
     */
    List<String> getChatIds(String type);
}
