package tchat.com.server;

import tchat.com.client.DialogMessage;
import tchat.com.client.ListMessage;
import tchat.com.client.LoginMessage;
import tchat.com.client.LogoutMessage;
import tchat.com.client.Message;
import tchat.com.client.TchatException;
import tchat.com.client.TchatService;
import tchat.com.client.UserDTO;
import tchat.com.shared.TchatVerifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TchatServiceImpl extends RemoteServiceServlet implements
TchatService {

	private int nextUserId;
	Map<Integer, User> users;

	public TchatServiceImpl() {
		nextUserId = 0;
		users = new HashMap<Integer, User>();
		Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	      public void run() {
	        try {
				cleanUsers();
			} catch (TchatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	    }, 2000, 4000);

	}

	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public UserDTO getUser(String nname) throws TchatException {
		User user = createUser(nname);
		for (User c : users.values()) 
			if (c!=user) c.addMessage(new LoginMessage(nname));
		List<String> nicknames = new ArrayList<String>();
		for (User u : users.values())
			nicknames.add(u.getNickname());
		user.addMessage(new ListMessage(nicknames));
		return user.getDTO();
	}


	@Override
	  public Message getNewMessage(UserDTO userDTO)
	      throws TchatException {
	    User user = findUser(userDTO);
	    user.setConnectionState(true);
	    try {
	      return user.getMessage();
	    } catch (InterruptedException e) {
	      return null;
	    } finally {
	      user.setConnectionState(false);
	    }
	  }

	@Override
	public void sendNewMessage(UserDTO userDTO, String message)  
			throws TchatException {
		User user = findUser(userDTO);
		message = "<b>"+user.getNickname()+"</b> : "+escapeHtml(message);
		for (User c : users.values())
			c.addMessage(new DialogMessage(message));
	}

	private User createUser(String nname) throws TchatException {
		if (!TchatVerifier.isValidNickname(nname))
			throw new TchatException(TchatException.Type.IncorrectNickname);
		 User user = new User(nname, nextUserId, 
			        getThreadLocalRequest().getSession().getId());
		nextUserId++;
		users.put(user.getId(), user);
		return user;
	}

	@Override
	public void closeUser(UserDTO userDTO) throws TchatException {
		User user = findUser(userDTO);
		closeUser(user);
	}

	private void closeUser(User user) throws TchatException {
		for (User c : users.values()) 
			if (c!=user) 
				c.addMessage(new LogoutMessage(user.getNickname()));
		users.remove(user.getId());
	}

	 private User findUser(UserDTO userDTO) throws TchatException {
		    int id = userDTO.getId();
		    User user = users.get(id);
		    if (user == null) throw new TchatException(TchatException.Type.NotLogged);
		    String  sessionId = getThreadLocalRequest().getSession().getId();
		    if (!user.getSessionId().equals(sessionId))
		      throw new TchatException(TchatException.Type.ErrorConnection);
		    return user;
		  }
	
	private void cleanUsers() throws TchatException {
	    List<User> usersToClose = new ArrayList<User>();
	    long now = System.currentTimeMillis();
	    for (User user : users.values())
	      if (now - user.lastConnection() >= 2000) {
	        usersToClose.add(user);
	      }
	    for (User user : usersToClose)
	      closeUser(user);
	  }
}