package org.freelo.controller.dashboard;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import org.freelo.model.users.FriendsDAO;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.Dashboard.Subwindows.addFriendWindow;

/**
 * Created by Artur on 1/21/2015.
 */
public class addFriendController {

  /*  private addFriendWindow friendWindow;

    public addFriendController(addFriendWindow friendUI){
        this.friendWindow = friendUI;
        setFriendAddButtonListener();
    }

    public void setFriendAddButtonListener(){
        friendWindow.addFriendButton.addClickListener(new addFriendButtonClickListenerEventHandler());
    }

    class addFriendButtonClickListenerEventHandler implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {

            if (friendWindow.friendField.getValue().isEmpty()) return;

            int friendID = UserManagement.getUserID(friendWindow.friendField.getValue());

            System.out.println("user: ");
            // todo user doesnt exist
            User user = (User) VaadinSession.getCurrent().getAttribute("userClass");

            System.out.println(user.getId() +" "+ user.getEmail());

        }
    }*/
}
