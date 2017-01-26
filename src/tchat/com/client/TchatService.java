package tchat.com.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("tchat")
public interface TchatService extends RemoteService {
	UserDTO getUser(String nickname) throws TchatException;
	Message getNewMessage(UserDTO user) throws TchatException;
	void closeUser(UserDTO user) throws TchatException;
	void sendNewMessage(UserDTO user, String message) throws TchatException;
}