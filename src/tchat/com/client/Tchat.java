package tchat.com.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import tchat.com.shared.TchatVerifier;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tchat implements EntryPoint {

	private final TchatServiceAsync tchatService = GWT.create(TchatService.class);

	private TchatPanel tchatPanel;
	private UserDTO user;
	private final TchatConstants constants = GWT.create(TchatConstants.class);

	
	
	public void onModuleLoad() {
		tchatPanel = new TchatPanel(this);
		RootPanel.get("tchatpanel").add(tchatPanel);
		NicknameDialogBox nicknameDialogBox = new NicknameDialogBox();
		nicknameDialogBox.show();
	}

	
	
	private void closeUser() {
		if (user==null) return;
		tchatService.closeUser(user, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	
	private void getUser(String nname) {
		tchatService.getUser(nname, new AsyncCallback<UserDTO>() {
			@Override
			public void onSuccess(UserDTO result) {
				user = result;
				getNewMessage();

			}

			@Override
			public void onFailure(Throwable caught) {
				displayError(caught.getMessage());
			}
		});
	}
	

	private void getNewMessage() {
		tchatService.getNewMessage(user, new AsyncCallback<Message>() {

			@Override
			public void onSuccess(Message result) {
				if (result!=null) result.accept(Tchat.this);
				getNewMessage();
			}

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof TchatException) 
					displayError(caught.getMessage());
			}
		});
	}
	

	public void addMessage(String msg) {
		tchatPanel.addMessage(msg);
	}
	

	protected void sendMessage(String msg) {
		if (user == null)
			displayError(constants.errorNotLogged());
		tchatService.sendNewMessage(user, msg, new AsyncCallback<Void>() {

			public void onSuccess(Void result) {
				tchatPanel.clearNewMessage();
			}

			public void onFailure(Throwable caught) {
				displayError(caught.getMessage());
			}
		});
	}
	

	private void displayError(String msg) {
		Window.alert(msg);
	}

	
	private class NicknameDialogBox extends DialogBox {

		private final TextBox nickname;

		public NicknameDialogBox() {
			super(false, true);

			setText(constants.chooseNickname());

			final FlowPanel formPanel = new FlowPanel();

			final Label label = new Label(constants.nickname());
			nickname = new TextBox();
			final Button button = new Button(constants.ok());

			formPanel.add(label);
			formPanel.add(nickname);
			formPanel.add(button);  

			setWidget(formPanel);

			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) { 
					validate();
				}
			});

			nickname.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
						validate();
				}
			});

			center();
		}

		private void validate() {
			String nname = nickname.getText();
			if (TchatVerifier.isValidNickname(nname)) {
				getUser(nname);
				hide();
			} else {
				displayError(constants.errorIncorrectNickname());
			}
		}

	}
	

	public void addNickname(String nickname) {
		tchatPanel.addNickname(nickname);
	}

	
	public void removeNickname(String nickname) {
		tchatPanel.removeNickname(nickname);
	}


}
