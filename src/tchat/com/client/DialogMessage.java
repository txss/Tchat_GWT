package tchat.com.client;

@SuppressWarnings("serial")
public class DialogMessage implements Message {

  private String message;
  
  public DialogMessage() {
  }
  
  public DialogMessage(String message) {
    this.message = message;
  }
  
  @Override
  public void accept(Tchat tchat) {
    tchat.addMessage(message);
  }
  
}