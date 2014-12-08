package org.freelo.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.vaadin.ui.Window;

import java.awt.*;


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


        //containers
        final HorizontalLayout container = new HorizontalLayout();
        container.addStyleName("container");
        container.setWidth("100%");
        container.setHeight("80%");
        addComponent(container);

        final VerticalLayout container2 = new VerticalLayout();
        container2.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        container2.addStyleName("container2");
        container2.setWidth("100%");
        addComponent(container2);

        VerticalLayout side_container = new VerticalLayout();
        side_container.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        side_container.addStyleName("side_container");




        // -------------------------------------

/*
        CssLayout ongoing = new CssLayout();
        ongoing.addStyleName("content");
        container.addComponent(ongoing);
        Label titleOngoing = new Label("ON GOING");
        titleOngoing.addStyleName("title");
        ongoing.addComponent(titleOngoing);

        CssLayout done = new CssLayout();
        done.addStyleName("content");
        container.addComponent(done);
        Label titleDone = new Label("DONE");
        titleDone.addStyleName("title");
        done.addComponent(titleDone);
*/
        // NEW TASK PAGE
        final Panel panel = new Panel("New Task Page");
        panel.addStyleName("panel");
        panel.setWidth("50%");
        //container2.setHeight("80%");
        // SIDE MENU
        Panel sidepanel = new Panel("Menu");
        sidepanel.addStyleName("sidepanel");
        // ELEMENTS
        final TextField tasktitle = new TextField("Task title");
        final TextField tasksssignee = new TextField("Assignee");
        tasksssignee.setInputPrompt("Search by Name");
        final Button addcomponent_button = new Button("Add Card");
//        addcomponent_button.addStyleName("button1");
//        addcomponent_button.addClickListener(new Button.ClickListener() {
//            private static final long serialVersionUID = 2181474159749123339L;
//
//            public void buttonClick(Button.ClickEvent event) {
//                todo.addComponent(new TaskCard());
//            }
//        });
        Button logout = new Button("Logout", new Button.ClickListener() {
            private static final long  serialVersionUID = -3494334621547144379L;

            @Override
            public void buttonClick(Button.ClickEvent event) {

                // "Logout" the user
                getSession().setAttribute("user", null);

                // Refresh this view, should redirect to login view
                getUI().getNavigator().navigateTo(NAME);
            }
        });

        final Button CreateNewProject = new Button("Create Project", new Button.ClickListener() {
            private static final long serialVersionUID = -2385924589892359849L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Window CreateNewProj = new Subwindow(container);
                UI.getCurrent().addWindow(CreateNewProj);
            }
        });
        PopupDateField create_prompt = new PopupDateField("Create Project");
        create_prompt.setInputPrompt("Set project parameters");


        //adding elements to containers


        side_container.addComponent(CreateNewProject);
        side_container.addComponent(logout);
        sidepanel.setContent(side_container);
        addComponent(panel);
        addComponent(sidepanel);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        String username = String.valueOf(getSession().getAttribute("user"));
        Notification welcome = new Notification("Welcome  " + username);
        welcome.setDelayMsec(30000);
        welcome.setPosition(Position.MIDDLE_CENTER);
        welcome.show(Page.getCurrent());

    }

    public class TaskCard extends VerticalLayout {
        private static final long serialVersionUID = 4924234591401040269L;

        public TaskCard() {
            String UserName = String.valueOf(getSession().getAttribute("user"));

            final CssLayout taskCard = new CssLayout();
            taskCard.addStyleName("task-card");
            taskCard.setWidth("90%");
            taskCard.setHeight("100px");
            addComponent(taskCard);

            Label taskTitle = new Label("Test");
            taskTitle.addStyleName("taskTitle");
            taskCard.addComponent(taskTitle);

            Label taskAssignee = new Label(UserName);
            taskTitle.addStyleName("taskAssignee");
            taskCard.addComponent(taskAssignee);


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

    }


}

