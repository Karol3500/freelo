package org.freelo.controller.dashboard;

import org.freelo.view.Dashboard.DashboardMenu;

/**
 * Created by Adrian on 14-01-2015.
 */
public class DashboardController {
    DashboardMenu dm = new DashboardMenu();

    DashboardController(DashboardMenu dm){
        this.dm = dm;

       // dm.friends = UserManagement.getUserFriends(String userName);

        return;
    }

}
