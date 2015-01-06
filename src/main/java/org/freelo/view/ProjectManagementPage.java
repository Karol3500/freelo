package org.freelo.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.freelo.view.Dashboard.DashboardMenuBean;
import org.freelo.view.tasks.TaskPage;
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
        container.setHeight("100%");
        addComponent(container);


        panel = new Panel("My Projects");
        container2.addStyleName("projectPanelContainer");
        panel.setSizeFull();
        panel.setWidth("1000px");
        panel.setContent(container2);

        //if (!SimpleLoginView.isAssigned) {
        //    Label NotAssignedLabel = new Label("You are not assigned to any project yet. You can create your own project by clicking 'Add project' button");
        //    container2.addComponent(NotAssignedLabel);
        //}
    }

    public class Subwindow extends Window {
        private static final long serialVersionUID = 5678234591401040269L;
        //        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        public Subwindow(final VerticalLayout container2) {
            super("New project");

            center();
            HorizontalLayout main = new HorizontalLayout();
            main.addStyleName("projectpopup");
            main.setSizeFull();
            setContent(main);
            setHeight("200px");
            setWidth("300px");
            setPositionY(50);
            setPositionX(50);

            final TextField ProjectName = new TextField("Enter project name");
            ProjectName.focus();
            // todo.add date
            final Button CreateButton = new Button("Create", new Button.ClickListener() {
                private static final long serialVersionUID = 2181474159749122119L;
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    final String name = ProjectName.getValue();
                    container2.addComponent(new ProjectItem(name));

                    close();
                }
            });
            CreateButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            VerticalLayout itemplacement = new VerticalLayout();
            itemplacement.setSizeFull();
            itemplacement.addComponent(ProjectName);
            itemplacement.addComponent(CreateButton);
            main.addComponent(itemplacement);

        }
    }

    @PostConstruct
    private void setup(){
        addProjectButton = new Button("Add project...", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //Method to display project creation popup called here
                Window CreateNewProj = new Subwindow(container2);
                UI.getCurrent().addWindow(CreateNewProj);
            }
        });
        container2.addComponent(addProjectButton);
        container.addComponent(dashboardMenuBean.getNewDashboardMenu());
        container.addComponent(panel);
    }

    public class ProjectItem extends HorizontalLayout{
        public ProjectItem(String name) {
            setSizeFull();
            HorizontalLayout container = new HorizontalLayout();
            container.setWidth("100%");
            addComponent(container);

            final Button ProjectButton = new Button(name, new Button.ClickListener() {
                private static final long serialVersionUID = 2181474159879122119L;
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    getUI().getNavigator().navigateTo(TaskPage.NAME);
                }
            });
            ProjectButton.addStyleName("ProjectButton");
            ProjectButton.setWidth("100%");
            container.addComponent(ProjectButton);
        }


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

