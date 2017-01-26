package tchat.com.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TchatPanel extends Composite {

	private Tchat tchat;

	private static TchatPanelUiBinder uiBinder = GWT
			.create(TchatPanelUiBinder.class);

	interface TchatPanelUiBinder extends UiBinder<Widget, TchatPanel> {
	}

	@UiField ScrollPanel scrollPanel;
	@UiField(provided = true) ListBox nicknameList = new ListBox(true);
	@UiField TextBox newMessageBox;
	@UiField HTML messagesBox;

	public TchatPanel(Tchat tchat) {
		initWidget(uiBinder.createAndBindUi(this));
		this.tchat = tchat;
	}

	@UiHandler("newMessageButton")
	void onClick(ClickEvent e) {
		tchat.sendMessage(newMessageBox.getText());
	}

	public void clearNewMessage() {
		newMessageBox.setText("");
	}

	public void addMessage(String msg) {
		messagesBox.setHTML(messagesBox.getHTML() + msg + "<br>");
		scrollPanel.scrollToBottom();
	}

	public void addNickname(String nickname) {
		nicknameList.insertItem(nickname, nicknameList.getItemCount());
	}

	public void removeNickname(String nickname) {
		for (int i = 0; i < nicknameList.getItemCount(); i++)
			if (nicknameList.getItemText(i).equals(nickname)) {
				nicknameList.removeItem(i);
				return;
			}
	}

}