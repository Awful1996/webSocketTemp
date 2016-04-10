package com.bsuir.chat.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat_messages")
public class ChatMessage{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
	private int id;

    @Column(name = "room")
    private String room;

    @Column(name = "message")
	private String message;

    @Column(name = "sender")
	private String sender;

    @Column(name = "received")
	private Date received;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public final String getMessage() {
		return message;
	}

	public final void setMessage(final String message) {
		this.message = message;
	}

	public final String getSender() {
		return sender;
	}

	public final void setSender(final String sender) {
		this.sender = sender;
	}

	public final Date getReceived() {
		return received;
	}

	public final void setReceived(final Date received) {
		this.received = received;
	}

	@Override
	public String toString() {
		return "ChatMessage [message=" + message + ", sender=" + sender
				+ ", received=" + received + "]";
	}
}
