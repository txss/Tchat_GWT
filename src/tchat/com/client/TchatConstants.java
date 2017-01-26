package tchat.com.client;

import com.google.gwt.i18n.client.Constants;

public interface TchatConstants extends Constants{

	@DefaultStringValue("Send")
	String send();

	@DefaultStringValue("Choose your nickname")
	String chooseNickname();

	@DefaultStringValue("Nickname:")
	String nickname();

	@DefaultStringValue("Ok")
	String ok();

	@DefaultStringValue("Incorrect nickname.")
	String errorIncorrectNickname();

	@DefaultStringValue("You are not logged.")
	String errorNotLogged();

	@DefaultStringValue("Connection error.")
	String errorConnection();
}
