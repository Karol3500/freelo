package org.freelo.controller.dashboard;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import org.freelo.model.users.FriendsDAO;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.Dashboard.DashboardMenu;
import org.freelo.view.Dashboard.Subwindows.AddFriendWindow;

/**
 * Created by Artur on 1/21/2015.
 */
public class addFriendController {

    private AddFriendWindow friendWindow;
    private DashboardMenu dashboardMenu;

    public addFriendController(DashboardMenu dashboardMenu){
        this.dashboardMenu = dashboardMenu;
    }

    public void addFriend(AddFriendWindow friendUI){
        this.friendWindow = friendUI;
        this.friendWindow.addFriendButton.addClickListener(new addFriendButtonClickListenerEventHandler());
    }

    class addFriendButtonClickListenerEventHandler implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {

            if (friendWindow.friendField.getValue().isEmpty()) return;

            int friendID = UserManagement.getUserID(friendWindow.friendField.getValue());

            // todo user doesnt exist
            User user = (User) VaadinSession.getCurrent().getAttribute("userClass");

            //System.out.println("user: "+user.getEmail());
            //System.out.println("friend id: "+friendID);

            FriendsDAO.addFriend(user.getId(),friendID);

            //DashboardMenu

        }
    }
}
