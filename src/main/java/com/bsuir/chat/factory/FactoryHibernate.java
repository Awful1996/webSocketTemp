package com.bsuir.chat.factory;


import com.bsuir.chat.DAO.ChatMessageDAO;
import com.bsuir.chat.DAO.impl.ChatMessageDAOImpl;

/**
 * Created by Антон on 4/2/2016.
 */
public class FactoryHibernate {

    private static ChatMessageDAO chatMessageDAO = null;
    private static FactoryHibernate instance = null;

    public static synchronized FactoryHibernate getInstance(){
        if (instance == null){
            instance = new FactoryHibernate();
        }
        return instance;
    }

    public ChatMessageDAO getChatMessageDAO(){
        if (chatMessageDAO == null){
            chatMessageDAO = new ChatMessageDAOImpl();
        }
        return chatMessageDAO;
    }
}
