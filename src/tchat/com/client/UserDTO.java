package tchat.com.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDTO implements Serializable {

  private int id;
  
  public UserDTO() {
  }

  public UserDTO(int id) {
    this.id = id;
  }
  
  public int getId() {
    return id;
  }
  
}