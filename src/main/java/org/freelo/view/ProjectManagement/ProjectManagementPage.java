package org.freelo.view.ProjectManagement;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.freelo.controller.projects.ProjectManagementPageController;
import org.freelo.view.Dashboard.DashboardMenuBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Jan on 2014-12-08.
 */

@Component
@Scope("prototype")
public class ProjectManagementPage extends HorizontalLayout implements View{

    private static final long serialVersionUID = -9002670791091569418L;
    public static final String NAME = "Project Management";
    public ProjectManagementPageController pageController;
    public Subwindow createNewProj;
    public addSprintWindow createNewSprint;
    public manageProjectWindow manageProject;

    @Autowired
    DashboardMenuBean dashboardMenuBean;
    String name;
    public Button addProjectButton;
    final VerticalLayout container2 = new VerticalLayout();
    HorizontalLayout container;
    Panel panel;

    public ProjectManagementPage() {
        setSizeFull();
        container = new HorizontalLayout();
        container.setSizeFull();
        addComponent(container);


        panel = new Panel("My Projects");
        container2.addStyleName("projectPanelContainer");
        container2.setSpacing(true);
        panel.setSizeFull();
        //panel.setWidth("100%");
        panel.setContent(container2);

    }

    public void addProject(String email, String name) {
        container2.addComponent(new ProjectItem(name, email));
    }

    @PostConstruct
    private void setup(){
        pageController = new ProjectManagementPageController(this);
        addProjectButton = new Button("Add project...", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //Method to display project creation popup called here
                createNewProj = new Subwindow(container2);
                UI.getCurrent().addWindow(createNewProj);
            }
        });
        container2.addComponent(addProjectButton);
        container.addComponent(dashboardMenuBean.getNewDashboardMenu());
        container.addComponent(panel);
        container.setExpandRatio(panel, 1f);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        String username = String.valueOf(getSession().getAttribute("user"));
        name = username;
        Notification welcome = new Notification("Welcome  " + username);
        welcome.setDelayMsec(1500);
        welcome.setPosition(Position.BOTTOM_CENTER);
        welcome.show(Page.getCurrent());
    }



}