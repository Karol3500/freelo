package org.freelo.controller.dashboard;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.freelo.model.users.*;
import org.freelo.view.Dashboard.DashboardMenu;
import org.freelo.view.Dashboard.Subwindows.addFriendWindow;
import org.freelo.view.Dashboard.Subwindows.MessageWindow;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Artur on 1/21/2015.
 */
public class FriendController {

    private addFriendWindow friendWindow;
    private DashboardMenu dashboardMenu;
    private MessageWindow messageWindow;
    String friendName = null;
    User user = null;
    public FriendController(DashboardMenu dashboardMenu){
        this.dashboardMenu = dashboardMenu;
    }

    public void addFriend(addFriendWindow friendUI){
        this.friendWindow = friendUI;
        this.friendWindow.addFriendButton.addClickListener(new addFriendButtonClickListenerEventHandler());
        this.friendWindow.friendField.addValidator(new UserValidator());
        this.friendWindow.friendField.addValidator(new EmailValidator("Username must be an email."));
    }

    public void addMessage(MessageWindow messageUI){
        this.messageWindow = messageUI;
        this.messageWindow.deleteButton.addClickListener(new deleteFriendButtonClickListenerEventHandler());
        this.messageWindow.sendButton.addClickListener(new sendMessageButtonClickListenerEventHandler());
        showMessages();
    }

    class addFriendButtonClickListenerEventHandler implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            friendWindow.friendField.setValidationVisible(false);
            try {
                user = (User) VaadinSession.getCurrent().getAttribute("userClass");
                friendName = friendWindow.friendField.getValue();
                friendWindow.friendField.validate();
                int friendID = UserManagement.getUserID(friendName);

                if (user.getId() == friendID) throw new Validator.InvalidValueException("Your email cannot be added.");
                FriendsDAO.addFriend(user.getId(),friendID);
                updateFriends();

                Notification success = new Notification(friendName + " added successfully.");
                success.setDelayMsec(1500);
                success.show(Page.getCurrent());
                friendWindow.close();
            } catch (Validator.InvalidValueException e) {
                if (e.getMessage() == null) Notification.show("Username must be an email.");
                else Notification.show(e.getMessage());
                friendWindow.friendField.setValidationVisible(true);
            }

        }
    }

    class UserValidator implements Validator {
        private static final long serialVersionUID = 1L;

        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (((String)value).isEmpty())
                throw new InvalidValueException("Username field cannot be empty.");
            if (!(value instanceof String &&
                    (UserManagement.checkUserID(friendName))))
                throw new InvalidValueException("User: " + friendName
                        + " doesn't exists.");

        }
    }

    class deleteFriendButtonClickListenerEventHandler implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            User user = (User) VaadinSession.getCurrent().getAttribute("userClass");
            FriendsDAO.deleteFriend(user.getId(),messageWindow.friend.getId());
            updateFriends();
            messageWindow.close();
            messageWindow = null;
        }
    }

    class sendMessageButtonClickListenerEventHandler implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            User sender = (User) VaadinSession.getCurrent().getAttribute("userClass");
            MessageDAO.sendMessage(sender.getId(),messageWindow.friend.getId(),messageWindow.messageToSendField.getValue());
            messageWindow.messageToSendField.setValue("");
            showMessages();
        }
    }

    public void showMessages(){
        User sender = (User) VaadinSession.getCurrent().getAttribute("userClass");
        List<Message> messages = MessageDAO.getMessages(sender.getId(),messageWindow.friend.getId());
        messages.addAll(MessageDAO.getMessages(messageWindow.friend.getId(),sender.getId()));
        Collections.sort(messages, new Comparator<Message>() {
            public int compare(Message c1, Message c2) {
                return c1.getDate().compareTo(c2.getDate());
            }
        });
        //todo delete before commit
        //todo end
        messageWindow.messageHistory = "";
        for (Message element: messages){
            messageWindow.addMessage(element.getDate(),UserManagement.getUser(element.getSender()).getFirstName(),element.getMessage());
            MessageDAO.setAsRead(element.getId());
        }
    }

    public void showFriends(){
        User user = (User) VaadinSession.getCurrent().getAttribute("userClass");
        List<Friends> friends = FriendsDAO.getFriends(user.getId());
        if (friends.isEmpty()) return;
        for (Friends friend:friends){
            user = UserManagement.getUser(friend.getFriendID());
            dashboardMenu.buildFriend(user.getFirstName(),user.getLastName(), user.getId());
        }
    }


    public void updateFriends(){
        this.dashboardMenu.removeFriends();
        showFriends();
    }

}
