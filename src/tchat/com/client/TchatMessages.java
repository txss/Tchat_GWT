package tchat.com.client;

import com.google.gwt.i18n.client.Messages;

public interface TchatMessages extends Messages{
	@DefaultMessage("<b>{0}</b> left the chat.")
	String leaveMessage(String nickname);

	@DefaultMessage("Welcome to <b>{0}</b>.")
	String welcomeMessage(String nickname);
}
