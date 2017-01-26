package tchat.com.server;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import tchat.com.client.Message;
import tchat.com.client.UserDTO;

public class User {

	private int id;
	private LinkedBlockingDeque<Message> messages;
	private String nickname;
	private long lastConnection;
	private boolean isConnected;
	private String sessionId;


	public User(String nickname, int id, String sessionId) {
		this.id = id;
		this.nickname = nickname;
		this.isConnected = false;
		this.sessionId = sessionId;
		this.lastConnection = System.currentTimeMillis();
		messages = new LinkedBlockingDeque<Message>();
	}

	public int getId() {
		return id;
	}

	UserDTO getDTO() {
		return new UserDTO(id);
	}

	public String getNickname() {
		return nickname;
	}

	Message getMessage() throws InterruptedException {
		return messages.pollFirst(2, TimeUnit.SECONDS);
	}

	public String getSessionId() {
		return sessionId;
	}

	void addMessage(Message s) {
		messages.add(s);
	}

	public void setConnectionState(boolean isConnected) {
		this.isConnected = isConnected;
		if (!isConnected) lastConnection = System.currentTimeMillis();
	}

	public long lastConnection() {
		if (!isConnected) return lastConnection;
		else return System.currentTimeMillis();
	}

}