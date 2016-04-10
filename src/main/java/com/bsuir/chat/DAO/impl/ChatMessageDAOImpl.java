package com.bsuir.chat.DAO.impl;

import com.bsuir.chat.DAO.ChatMessageDAO;
import com.bsuir.chat.entity.ChatMessage;
import com.bsuir.chat.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Антон on 4/2/2016.
 */
public class ChatMessageDAOImpl implements ChatMessageDAO {

    @Override
    public void addChatMessage(ChatMessage chatMessage) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(chatMessage);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    @Override
    public void updateChatMessage(Long chat_message_id, ChatMessage chatMessage) throws SQLException {

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(chatMessage);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public ChatMessage getChatMessageById(Long chat_message_id) throws SQLException {
        Session session = null;
        ChatMessage chatMessage = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            chatMessage = (ChatMessage) session.load(ChatMessage.class, chat_message_id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return chatMessage;
    }

    @Override
    public Collection getAllChatMessages() throws SQLException {
        Session session = null;
        List<ChatMessage> chatMessages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            chatMessages = session.createCriteria(ChatMessage.class).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return chatMessages;
    }

    @Override
    public void deleteChatMessage(ChatMessage bus) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(bus);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Collection getAllChatMessagesByRoom(String room) throws SQLException {
        Session session = null;
        List<ChatMessage> chatMessages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(ChatMessage.class);
            chatMessages = criteria.add(Restrictions.eq("room", room)).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return chatMessages;
    }
}
