package com.bsuir.chat.DAO;

import com.bsuir.chat.entity.ChatMessage;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Антон on 4/2/2016.
 */
public interface ChatMessageDAO {


        void addChatMessage(ChatMessage chatMessage) throws SQLException;
        void updateChatMessage(Long chat_message_id, ChatMessage chatMessage) throws SQLException;
        ChatMessage getChatMessageById(Long chat_message_id) throws SQLException;
        Collection getAllChatMessages() throws SQLException;
        void deleteChatMessage(ChatMessage bus) throws SQLException;
        Collection getAllChatMessagesByRoom(String room) throws SQLException;

}
