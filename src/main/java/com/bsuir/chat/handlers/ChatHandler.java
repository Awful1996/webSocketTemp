package com.bsuir.chat.handlers;

import com.bsuir.chat.DAO.ChatMessageDAO;
import com.bsuir.chat.entity.ChatMessage;
import com.bsuir.chat.factory.FactoryHibernate;
import com.bsuir.chat.image.ProcessImage;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/chat/{room}")
public class ChatHandler {
	private final Logger log = Logger.getLogger(getClass().getName());

	@OnOpen
	public void open(final Session session, @PathParam("room") final String room) {
		log.info("session openend and bound to room: " + room);
		session.getUserProperties().put("room", room);
        FactoryHibernate factoryHibernate = new FactoryHibernate();
        ChatMessageDAO chatMessageDAO = factoryHibernate.getChatMessageDAO();
        try {
            File f = new File("D:\\IdeaProjects\\webChat2.0\\src\\main\\resources\\github.jpg");
            FileInputStream imageInFile = new FileInputStream(f);
            byte imageData[] = new byte[(int) f.length()];
            imageInFile.read(imageData);
            String imageDataString = ProcessImage.encodeImage(imageData);
            JSONArray result = new JSONArray();
            List<ChatMessage> chatMessages = (List<ChatMessage>) chatMessageDAO.getAllChatMessagesByRoom(room);
            for(ChatMessage chatMessage:chatMessages){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", chatMessage.getMessage());
                jsonObject.put("received", chatMessage.getReceived());
                jsonObject.put("sender", chatMessage.getSender());
                jsonObject.put("image", imageDataString);
                result.put(jsonObject);
            }
            session.getBasicRemote().sendText(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	@OnMessage
	public void onMessage(Session session, String message) {
		String room = (String) session.getUserProperties().get("room");
		try {
            File f = new File("D:\\IdeaProjects\\webChat2.0\\src\\main\\resources\\github.jpg");
            FileInputStream imageInFile = new FileInputStream(f);
            byte imageData[] = new byte[(int) f.length()];
            imageInFile.read(imageData);
            String imageDataString = ProcessImage.encodeImage(imageData);
            JSONObject jsonObject = new JSONObject(message);
            jsonObject.put("image", imageDataString);
            FactoryHibernate factoryHibernate = FactoryHibernate.getInstance();
            ChatMessageDAO chatMessageDAO = factoryHibernate.getChatMessageDAO();
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage((String) jsonObject.get("message"));
            chatMessage.setReceived(new Date());
            chatMessage.setRoom(room);
            chatMessage.setSender((String) jsonObject.get("sender"));
            chatMessageDAO.addChatMessage(chatMessage);
            System.out.println(new Date());
            jsonObject.put("received", new Date());
            JSONArray result = new JSONArray();
            result.put(jsonObject);
            for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
						&& room.equals(s.getUserProperties().get("room"))) {
					s.getBasicRemote().sendText(result.toString());
				}
			}
		} catch (IOException e) {
			log.log(Level.WARNING, "onMessage failed", e);
		} catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
