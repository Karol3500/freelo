package org.freelo.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.freelo.controller.projects.CreateProjectSubwindowController;
import org.freelo.controller.projects.ProjectManagementPageController;
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

        //if (!SimpleLoginView.isAssigned) {
        //    Label NotAssignedLabel = new Label("You are not assigned to any project yet. You can create your own project by clicking 'Add project' button");
        //    container2.addComponent(NotAssignedLabel);
        //}
    }

    public void addProject(String email, String name) {
        container2.addComponent(new ProjectItem(name, email));
    }


///////////////////// Project Window


    public class Subwindow extends Window {
        private static final long serialVersionUID = 5678234591401040269L;
        //        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        public Button createButton;
        public ProjectItem pi;
        CreateProjectSubwindowController c;

        public Subwindow(final VerticalLayout container2) {
            super("New project");
            c = new CreateProjectSubwindowController(this);
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
            createButton = new Button("Create", new Button.ClickListener() {
                private static final long serialVersionUID = 2181474159749122119L;
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    pi = createProject(ProjectName, container2);
                    close();
                    c.createProject(pi);
                }
            });
            createButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            VerticalLayout itemplacement = new VerticalLayout();
            itemplacement.setSizeFull();
            itemplacement.addComponent(ProjectName);
            itemplacement.addComponent(createButton);
            main.addComponent(itemplacement);

        }



        public ProjectItem createProject(TextField projectName, VerticalLayout container2) {
            final String name = projectName.getValue();
            String manager = (String) VaadinSession.getCurrent().getAttribute("user");
            ProjectItem pi = new ProjectItem(name, manager);
            container2.addComponent(pi);
            return pi;
        }


    }



///////////////////// Sprint Window


    public class addSprintWindow extends Window {
        private static final long serialVersionUID = 5683290459141040269L;
        //        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //public Button createButton;
        //public ProjectItem pi;
        //CreateProjectSubwindowController c;

        public addSprintWindow(final VerticalLayout nextcontainer) {
            super("New sprint");
            //c = new CreateProjectSubwindowController(this);
            center();
            HorizontalLayout main = new HorizontalLayout();
            main.addStyleName("projectpopup");
            main.setSizeFull();
            setContent(main);
            setHeight("300px");
            setWidth("300px");
            setPositionY(50);
            setPositionX(50);

            final TextField ProjectName = new TextField("Enter sprint name");
            ProjectName.focus();
            // todo.add date
            PopupDateField startDatePicker = new PopupDateField("Start date");
            PopupDateField endDatePicker = new PopupDateField("End date");

            Button createButton = new Button("Create", new Button.ClickListener() {
                private static final long serialVersionUID = 2181474159749122119L;

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    //pi = createProject(ProjectName, container2);
                    close();
                    //c.createProject(pi);

                    SprintViewObject sprint = new SprintViewObject();
                    nextcontainer.addComponent(sprint.SprintButton);
                    sprint.SprintButton.addStyleName("SprintButton");
                    sprint.SprintButton.setWidth("100%");
                }
            });
            createButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            VerticalLayout itemplacement = new VerticalLayout();
            itemplacement.setSizeFull();
            itemplacement.addComponent(ProjectName);
            itemplacement.addComponent(startDatePicker);
            itemplacement.addComponent(endDatePicker);
            itemplacement.addComponent(createButton);
            main.addComponent(itemplacement);

        }

    }


///////////////////// Manage Project Window

    public class manageProjectWindow extends Window {
        private static final long serialVersionUID = 5683290459141040269L;
        //        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //public Button createButton;
        //public ProjectItem pi;
        //CreateProjectSubwindowController c;

        public manageProjectWindow() {
            super("Project");
            //c = new CreateProjectSubwindowController(this);
            center();
            HorizontalLayout main = new HorizontalLayout();
            main.addStyleName("projectpopup");
            main.setSizeFull();
            setContent(main);
            setHeight("300px");
            setWidth("300px");
            setPositionY(50);
            setPositionX(50);


            Button updateButton = new Button("Update", new Button.ClickListener() {
                private static final long serialVersionUID = 2181474159749122119L;

                @Override
                public void buttonClick(Button.ClickEvent event) {

                    close();


                }
            });
            updateButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            VerticalLayout itemplacement = new VerticalLayout();
            itemplacement.setSizeFull();
            itemplacement.addComponent(updateButton);
            main.addComponent(itemplacement);

        }

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

    public class ProjectItem extends HorizontalLayout {
        public String manager;
        public Button sprintButton;

        public ProjectItem(String name, String manager) {

            setSizeFull();
            HorizontalLayout container = new HorizontalLayout();
            final VerticalLayout nextcontainer = new VerticalLayout();
            nextcontainer.addStyleName("projectContainer");
            //nextcontainer.setHeight("200px");
            //nextcontainer.setSpacing(true);
            Panel ProjectPanel = new Panel(name);
            ProjectPanel.addStyleName("projectPanel");
            ProjectPanel.setWidth("100%");
            ProjectPanel.setContent(nextcontainer);
            container.setWidth("100%");
            addComponent(container);
            Button addSprintButton = new Button("Add sprint..", new Button.ClickListener() {
                private static final long serialVersionUID = 2181474159879122119L;

                @Override
                public void buttonClick(Button.ClickEvent event) {

                    createNewSprint = new addSprintWindow(nextcontainer);
                    UI.getCurrent().addWindow(createNewSprint);

                }


            });

            Button manageProjectButton = new Button("Manage project", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    //Method to display project creation popup called here
                    manageProject = new manageProjectWindow();
                    UI.getCurrent().addWindow(manageProject);
                }
            });
            container.addComponent(ProjectPanel);

            final HorizontalLayout buttonsContainer = new HorizontalLayout();
            buttonsContainer.addComponent(manageProjectButton);
            buttonsContainer.addComponent(addSprintButton);


            nextcontainer.addComponent(buttonsContainer);

        }
    }




    public class SprintViewObject {
        public final Button SprintButton = new Button("Sprint", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159879122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(TaskPage.NAME);
            }
        });
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
