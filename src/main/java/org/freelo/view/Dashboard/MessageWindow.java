package org.freelo.view.Dashboard;

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

/**
 * Created by Adrian on 14-01-2015.
 */
public class MessageWindow extends Window {
	private static final long serialVersionUID = 1L;
	private TextField messageToSendField;
    private TextArea messagesHistoryArea;
    private Button sendButton;

    private MessageWindow(final User user){
        super(user.getFirstName().substring(0, 1).toUpperCase()+ user.getFirstName().substring(1)+" "+
                user.getLastName().substring(0, 1).toUpperCase()+ user.getLastName().substring(1));
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setModal(true);
        setHeight(60.0f, Unit.PERCENTAGE);
        setWidth(60.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        //content = buildMessageLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, true, true, true));
        setContent(content);

        content.addComponent(buildMessageLayout());

    }

    VerticalLayout buildMessageLayout(){
        VerticalLayout messageLayout = new VerticalLayout();
        messageLayout.setSizeFull();
        messageLayout.setSpacing(true);
        Label messageWindowLabel = new Label("Messages");
        //tab1.setMargin(new MarginInfo(true, true, false, true));

        Button deleteButton = new Button("Remove Friend");
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

    public static void open(final User user) {
        Window w = new MessageWindow(user);
        UI.getCurrent().addWindow(w);
        w.focus();
    }

}
