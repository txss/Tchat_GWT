package tchat.com.client;

import com.google.gwt.core.client.GWT;

@SuppressWarnings("serial")
public class TchatException extends Exception {

	public enum Type {
		NotLogged,
		IncorrectNickname,
		ErrorConnection,
		Other
	}

	private Type type;

	public TchatException() {
	}

	public TchatException(Type type) {
		this.type = type;
	}

	public TchatException(String msg) {
		super(msg);
		this.type = Type.Other;
	}

	public String getMessage() {
		final TchatConstants constants = GWT.create(TchatConstants.class);
		switch (type) {
		case NotLogged:
			return constants.errorNotLogged();
		case ErrorConnection:
			return constants.errorConnection();
		case IncorrectNickname:
			return constants.errorIncorrectNickname();
		case Other:
			return super.toString();
		}
		return null;
	}
}
