package tchat.com.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TchatServiceAsync {
	void getUser(String nickname, AsyncCallback<UserDTO> callback);
	void getNewMessage(UserDTO user, AsyncCallback<Message> callback);
	void sendNewMessage(UserDTO user, String message,
			AsyncCallback<Void> callback);
	void closeUser(UserDTO user, AsyncCallback<Void> callback);
}