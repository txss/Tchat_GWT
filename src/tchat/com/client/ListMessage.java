package tchat.com.client;

import java.util.List;

@SuppressWarnings("serial")
public class ListMessage implements Message {

  private List<String> nicknames;
  
  public ListMessage() {
  }
  
  public ListMessage(List<String> nicknames) { 
    this.nicknames = nicknames;
  }
  
  @Override
  public void accept(Tchat tchat) {
    for (String nickname : nicknames)
      tchat.addNickname(nickname);
  }
  
}