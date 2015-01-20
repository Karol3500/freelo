package org.freelo.controller.dashboard;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import org.freelo.model.users.User;
import org.freelo.view.Dashboard.DashboardMenu;
import org.freelo.view.Dashboard.Subwindows.MessageWindow;

/**
 * Created by Adrian on 14-01-2015.
 */
public class DashboardController {
    DashboardMenu dm = new DashboardMenu();

    DashboardController(DashboardMenu dm){
        this.dm = dm;

        //dm.friendViewButton

       // dm.friends = UserManagement.getUserFriends(String userName);

        return;
    }

    public final class friendViewButton extends Button {
        private static final long serialVersionUID = 1L;

        public friendViewButton(User user) {
            setPrimaryStyleName("valo-menu-item");
            setIcon(FontAwesome.USER);
            setCaption(user.getFirstName().substring(0, 1).toUpperCase()+ user.getFirstName().substring(1)+" "+
                    user.getLastName().substring(0, 1).toUpperCase()+ user.getLastName().substring(1));

            addClickListener(new ClickListener() {
                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(final ClickEvent event) {
                    //.open(getCurrentUser());
                }
            });

        }
    }

}
