package org.freelo.view.Dashboard.Subwindows;

import org.freelo.controller.dashboard.FriendController;
import org.freelo.model.users.User;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.model.users.UserManagement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Adrian on 14-01-2015.
 */
public class MessageWindow extends Window {
	private static final long serialVersionUID = 1L;
	public TextField messageToSendField;
    public TextArea messagesHistoryArea;
    public String messageHistory;
    public Button sendButton;
    public User friend;
    public Button deleteButton;

    private FriendController friendController;

    private MessageWindow(User user, FriendController inFriendController) {
        super(user.getFirstName().substring(0, 1).toUpperCase() + user.getFirstName().substring(1) + " " +
                user.getLastName().substring(0, 1).toUpperCase() + user.getLastName().substring(1));
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setModal(true);
        setHeight(60.0f, Unit.PERCENTAGE);
        setWidth(60.0f, Unit.PERCENTAGE);
        this.friend = user;
        friendController = inFriendController;

        VerticalLayout content = new VerticalLayout();
        //content = buildMessageLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, true, true, true));
        setContent(content);

        content.addComponent(buildMessageLayout());
        friendController.addMessage(this);
        messageHistory = new String();
    }

    VerticalLayout buildMessageLayout(){
        VerticalLayout messageLayout = new VerticalLayout();
        messageLayout.setSizeFull();
        messageLayout.setSpacing(true);
        Label messageWindowLabel = new Label("Messages");
        //tab1.setMargin(new MarginInfo(true, true, false, true));

        deleteButton = new Button("Remove Friend");
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);

        messagesHistoryArea = new TextArea();
        //shoutBoxArea.setReadOnly(true);
        messagesHistoryArea.setEnabled(false);
        messagesHistoryArea.setSizeFull();
        messagesHistoryArea.setWordwrap(true);

        HorizontalLayout writeTextArea = buildWritingArea();
        writeTextArea.setWidth("90%");
        messageLayout.addComponents(deleteButton, messageWindowLabel, messagesHistoryArea, writeTextArea);
        messageLayout.setExpandRatio(messagesHistoryArea, 5.0f);
        messageLayout.setExpandRatio(writeTextArea, 1.0f);

        return messageLayout;
    }

    HorizontalLayout buildWritingArea(){
        HorizontalLayout writingArea = new HorizontalLayout();
        writingArea.setSpacing(true);

        messageToSendField = new TextField();
        sendButton = new Button("SEND");

        messageToSendField.setWidth("100%");
        messageToSendField.setValue("");
        messageToSendField.setNullRepresentation("");
        messageToSendField.setInputPrompt("Type message HERE...");
        messageToSendField.setImmediate(true);

        sendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        sendButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        sendButton.setIcon(FontAwesome.ENVELOPE);

        writingArea.addComponents(messageToSendField,sendButton);

        return writingArea;
    }

    public static void open(User user, int friendID, FriendController friendController) {
        User friend = UserManagement.getUser(friendID);
        Window w = new MessageWindow(friend, friendController);
        UI.getCurrent().addWindow(w);
        w.focus();
    }

    public void addMessage(Date date, String userName, String message){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss");
        messageHistory += ft.format(date)+" "+userName+":  "+message+"\n";
        messagesHistoryArea.setValue(messageHistory);
    }

}
