package org.freelo.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import org.freelo.model.users.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.vaadin.ui.Window;



/**
 * Created by Jan on 2014-11-08.
 */
@Component
@Scope("prototype")
public class TaskPage extends HorizontalLayout implements View {

    private static final long serialVersionUID = -906274928780939032L;
    public static final String NAME = "";


    public TaskPage() {
        setSizeFull();
        addStyleName("taskpage");

        final HorizontalLayout container = new HorizontalLayout();
        container.addStyleName("container");
        container.setWidth("100%");
        container.setHeight("100%");


//        final Panel panel = new Panel("New Task Page");
//        panel.addStyleName("panel");
//        panel.setWidth("100%");

//        Panel sidepanel = new Panel("Menu");
//        sidepanel.addStyleName("sidepanel");
//        sidepanel.setWidth("50%");

        final Panel todopanel = new Panel("TODO");
        final CssLayout todo = new CssLayout();
        todo.addStyleName("content");
        todopanel.addStyleName("todopanel");
        todopanel.setHeight("100%");
        todopanel.setContent(todo);
        //        container.addComponent(todopanel);
        // ONGOING
        final Panel ongoingpanel = new Panel("ON GOING");
        final CssLayout ongoing = new CssLayout();
        ongoing.addStyleName("content");
        ongoingpanel.addStyleName("ongoingpanel");
        ongoingpanel.setHeight("100%");
        ongoingpanel.setContent(ongoing);
        //        container.addComponent(ongoingpanel);
        // DONE
        final Panel donepanel = new Panel("DONE");
        final CssLayout done = new CssLayout();
        done.addStyleName("content");
        donepanel.addStyleName("donepanel");
        donepanel.setHeight("100%");
        donepanel.setContent(done);
//        container.addComponent(donepanel);

        addComponent(SideBar(container, todo));
        addComponent(container);
    }

    public MenuBar SideBar(final HorizontalLayout container, final CssLayout todo) {

        final MenuBar settings = new MenuBar();
        settings.addStyleName("sidebar");
        settings.addStyleName("user-menu");
        settings.addStyleName("no-vertical-drag-hints");
        settings.addStyleName("no-horizontal-drag-hints");
        final String user = getCurrentUser();

        MenuBar.MenuItem settingsitem = settings.addItem("", new ThemeResource(
                "img/profile-pic-300px.jpg"), null);
        settingsitem.setText(user);

        settings.addItem("Create new Project", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
                Window CreateNewProject = new Subwindow(container);
                UI.getCurrent().addWindow(CreateNewProject);
            }
        });

        settings.addItem("Add Task", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
                Window CreateNewTask = new Subwindow(todo);
                UI.getCurrent().addWindow(CreateNewTask);
            }
        });

        settingsitem.addItem("Edit Profile", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
                // not implemented yet
            }
        });
        settingsitem.addItem("Preferences", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
                // not yet implemented
            }
        });
        settingsitem.addItem("Sign Out", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
                getSession().setAttribute("user", null);
//                // Refresh this view, should redirect to login view
                getUI().getNavigator().navigateTo(NAME);
            }
        });

        return settings;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        String username = getCurrentUser();
        Notification welcome = new Notification("Welcome  " + username);
        welcome.setDelayMsec(20000);
        welcome.setPosition(Position.MIDDLE_CENTER);
        welcome.show(Page.getCurrent());

    }

    public String getCurrentUser() {
        ViewChangeListener.ViewChangeEvent viewChangeEvent;
//        String username = String.valueOf(getSession().getAttribute("user"));
        return null;
    }

    public class TaskCard extends VerticalLayout {
        private static final long serialVersionUID = 4924234591401040269L;

        public TaskCard(String name, String data) {
            final CssLayout taskCard = new CssLayout();
            taskCard.addStyleName("task-card");
            taskCard.setHeight("100px");
            addComponent(taskCard);

            taskCard.addComponent(new Label(name));
            taskCard.addComponent(new Label(data));

            final Button claimButton = new Button("Claim");
            claimButton.addStyleName("claimButton");
            /*
            claimButton.addClickListener(new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {
            todo.removeComponent(new TaskCard());
                }
           });
            taskCard.addComponent(claimButton);
        */
        }


    }

    public class Subwindow extends Window {
        private static final long serialVersionUID = 5678234591401040269L;
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        public Subwindow(final HorizontalLayout omg) {
            super("OMG");

            center();
            HorizontalLayout main = new HorizontalLayout();
            main.setSizeFull();
            setContent(main);
            setHeight("200px");
            setWidth("300px");
            setPositionY(50);
            setPositionX(50);

            //making panels
            final Panel todopanel = new Panel("TODO");
            final CssLayout todo = new CssLayout();
            todo.addStyleName("content");
            todopanel.addStyleName("todopanel");
            todopanel.setHeight("100%");
            todopanel.setContent(todo);
            //        container.addComponent(todopanel);
            // ONGOING
            final Panel ongoingpanel = new Panel("ON GOING");
            final CssLayout ongoing = new CssLayout();
            ongoing.addStyleName("content");
            ongoingpanel.addStyleName("ongoingpanel");
            ongoingpanel.setHeight("100%");
            ongoingpanel.setContent(ongoing);
            //        container.addComponent(ongoingpanel);
            // DONE
            final Panel donepanel = new Panel("DONE");
            final CssLayout done = new CssLayout();
            done.addStyleName("content");
            donepanel.addStyleName("donepanel");
            donepanel.setHeight("100%");
            donepanel.setContent(done);
//        container.addComponent(donepanel);


            final TextField ProjectName = new TextField("Enter project name");
            // todo.add date
            final Button CreateButton = new Button("Create", new Button.ClickListener() {
                private static final long serialVersionUID = 2181474159749122119L;
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    omg.addComponent(todopanel);
                    omg.addComponent(ongoingpanel);
                    omg.addComponent(donepanel);

                    close();
                }
            });
            VerticalLayout itemplacement = new VerticalLayout();
            itemplacement.setSizeFull();
            itemplacement.addComponent(ProjectName);
            itemplacement.addComponent(CreateButton);
            main.addComponent(itemplacement);

        }
        public Subwindow(final CssLayout todo) {
        //Appearance of the popup window
            super("OMG 2");
            VerticalLayout menu = new VerticalLayout();
            menu.setSizeFull();
            setContent(menu);
            setHeight("300px");
            setWidth("300px");
            setPositionX(200);
            setPositionY(150);

            final TextField TaskName = new TextField();
            TaskName.setInputPrompt("Name your Task");

            final TextField data = new TextField();
            data.setInputPrompt("Type what you want");

            final Button CreateTaskButton = new Button("Create Task", new Button.ClickListener() {
               private static final long serialVersionUID = -1181474151239122119L;
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    final String task_data = data.getValue();
                    final String T_name = TaskName.getValue();
                    todo.addComponent(new TaskCard(T_name, task_data));
                    close();
                }
            });
            menu.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            menu.addComponent(TaskName);
            menu.addComponent(data);
            menu.addComponent(CreateTaskButton);

        }
    }

}

