package org.freelo.controller.dashboard;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import org.freelo.model.users.Friends;
import org.freelo.model.users.FriendsDAO;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.Dashboard.DashboardMenu;
import org.freelo.view.Dashboard.Subwindows.addFriendWindow;

import java.util.List;

/**
 * Created by Artur on 1/21/2015.
 */
public class FriendController {

    private addFriendWindow friendWindow;
    private DashboardMenu dashboardMenu;

    public FriendController(DashboardMenu dashboardMenu){
        this.dashboardMenu = dashboardMenu;
    }

    public void addFriend(addFriendWindow friendUI){
        this.friendWindow = friendUI;
        this.friendWindow.addFriendButton.addClickListener(new addFriendButtonClickListenerEventHandler());
    }

    class addFriendButtonClickListenerEventHandler implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {

            if (friendWindow.friendField.getValue().isEmpty()) return;

            int friendID = UserManagement.getUserID(friendWindow.friendField.getValue());

            User user = (User) VaadinSession.getCurrent().getAttribute("userClass");

            System.out.println("user: "+user.getEmail());
            System.out.println("friend id: "+friendID);

            FriendsDAO.addFriend(user.getId(),friendID);

            dashboardMenu.removeFriends();
            showFriends();

        }
    }


    public void showFriends(){
        User user = (User) VaadinSession.getCurrent().getAttribute("userClass");
        List<Friends> friends = FriendsDAO.getFriends(user.getId());
        if (friends.isEmpty()) return;
        for (Friends friend:friends){
            user = UserManagement.getUser(friend.getFriendID());
            dashboardMenu.buildFriend(user.getFirstName(),user.getLastName());
        }
    }

    public void updateFriends(){
        this.dashboardMenu.removeFriends();
        showFriends();
    }

}
