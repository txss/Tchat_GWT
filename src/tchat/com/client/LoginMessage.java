package tchat.com.client;

import com.google.gwt.core.client.GWT;

@SuppressWarnings("serial")
public class LoginMessage implements Message {

  private String nickname;
  
  
  public LoginMessage() {
  }
  
  public LoginMessage(String nickname) { 
    this.nickname = nickname;
  }
  
  @Override
  public void accept(Tchat tchat) {
	  final TchatMessages messages = GWT.create(TchatMessages.class);
      tchat.addNickname(nickname);
      tchat.addMessage("<font color=\"#FF0000\">"+
    	        messages.welcomeMessage(nickname) +"</font>");
  }
}