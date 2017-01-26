package tchat.com.client;

import com.google.gwt.core.client.GWT;

@SuppressWarnings("serial")
public class LogoutMessage implements Message {

  private String nickname;
  
  public LogoutMessage() {
  }
  
  public LogoutMessage(String nickname) { 
    this.nickname = nickname;
  }
  
  @Override
  public void accept(Tchat tchat) {
	  final TchatMessages messages = GWT.create(TchatMessages.class);
      tchat.removeNickname(nickname);
      tchat.addMessage("<font color=\"#FF0000\">"
    	        + messages.leaveMessage(nickname) + "</font>");
  }
}