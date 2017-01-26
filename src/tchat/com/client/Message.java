package tchat.com.client;

import java.io.Serializable;

public interface Message extends Serializable {
	public void accept(Tchat tchat);
}