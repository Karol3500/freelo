package org.freelo.view.Dashboard;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.spring.VaadinComponent;


/**
 * Created by karol on 16.12.14.
 */
@Component
@Scope("session")
public class DashboardMenuBean {
    DashboardMenu dashboardMenu;

    public DashboardMenuBean(){
        dashboardMenu = new DashboardMenu();
    }

    public DashboardMenu getDashboardMenu(){
        return dashboardMenu;
    }
}
