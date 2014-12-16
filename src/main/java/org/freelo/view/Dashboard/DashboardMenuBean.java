package org.freelo.view.Dashboard;

import org.freelo.view.SimpleLoginUI;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by karol on 16.12.14.
 */
@Component
@Scope("session")
public class DashboardMenuBean {
    List<DashboardMenu> dashboards;

    public DashboardMenuBean(){
        dashboards = new ArrayList<>();
    }

    public void initUIs(SimpleLoginUI ui){
        for(DashboardMenu d : dashboards){
            d.setupUI(ui);
        }
    }

    public DashboardMenu getNewDashboardMenu(){
        DashboardMenu d = new DashboardMenu();
        dashboards.add(d);
        return d;
    }
}
