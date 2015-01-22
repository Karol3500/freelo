package org.freelo.controller.dashboard;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import org.freelo.model.users.*;
import org.freelo.view.Dashboard.DashboardMenu;
import org.freelo.view.Dashboard.Subwindows.MessageWindow;
import org.freelo.view.Dashboard.Subwindows.addFriendWindow;

import javax.jws.soap.SOAPBinding;
import java.text.SimpleDateFormat;
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

    public FriendController(DashboardMenu dashboardMenu){
        this.dashboardMenu = dashboardMenu;
    }

    public void addFriend(addFriendWindow friendUI){
        this.friendWindow = friendUI;
        this.friendWindow.addFriendButton.addClickListener(new addFriendButtonClickListenerEventHandler());
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
            if (friendWindow.friendField.getValue().isEmpty()) return;
            int friendID = UserManagement.getUserID(friendWindow.friendField.getValue());
            User user = (User) VaadinSession.getCurrent().getAttribute("userClass");
            if (user.getId() == friendID) return;
            FriendsDAO.addFriend(user.getId(),friendID);
            updateFriends();
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
