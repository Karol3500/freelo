package org.freelo.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

/**
 * Created by Jan on 2014-11-08.
 */

public class TaskPage extends HorizontalLayout implements View{
    public static final String NAME = "";
    public TaskPage() {
        setSizeFull();
        addStyleName("taskpage");


        HorizontalLayout container = new HorizontalLayout();
        container.addStyleName("container");
        container.setWidth("100%");
        container.setHeight("80%");
        addComponent(container);

        final CssLayout todo = new CssLayout();
        todo.addStyleName("content");
        container.addComponent(todo);
        Label titleTodo = new Label("TODO");
        titleTodo.addStyleName("title");
        todo.addComponent(titleTodo);


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


        Panel panel = new Panel("New Task Page");
        panel.addStyleName("panel");
        VerticalLayout container2 = new VerticalLayout();
        container2.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        container2.addStyleName("container2");
        panel.setWidth("50%");
        //container2.setHeight("80%");


        final TextField tasktitle = new TextField("Task title");
        final TextField tasksssignee = new TextField("Assignee");
        tasksssignee.setInputPrompt("Search by Name");
        final Button button1 = new Button("Add Card");
        button1.addStyleName("button1");
        button1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                todo.addComponent(new TaskCard());
                todo.addComponent(new TaskCard2());


            }
        });
        Button logout = new Button("Logout", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {

                // "Logout" the user
                getSession().setAttribute("user", null);

                // Refresh this view, should redirect to login view
                getUI().getNavigator().navigateTo(NAME);
            }
        });

        container2.addComponent(tasktitle);
        container2.addComponent(tasksssignee);
        container2.addComponent(button1);
        container2.addComponent(logout);
        panel.setContent(container2);

        addComponent(panel);
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

        public TaskCard() {
            final CssLayout taskCard = new CssLayout();
            taskCard.addStyleName("task-card");
            taskCard.setWidth("90%");
            taskCard.setHeight("100px");
            addComponent(taskCard);

            Label taskTitle = new Label("Task example 1");
            taskTitle.addStyleName("taskTitle");
            taskCard.addComponent(taskTitle);

            Label taskAssignee = new Label("Jan Dziergwa");
            taskTitle.addStyleName("taskAssignee");
            taskCard.addComponent(taskAssignee);

        }

    }

    public class TaskCard2 extends VerticalLayout {

        public TaskCard2() {
            final CssLayout taskCard = new CssLayout();
            taskCard.addStyleName("task-card");
            taskCard.setWidth("90%");
            taskCard.setHeight("100px");
            addComponent(taskCard);

            Label taskTitle = new Label("Task example 2");
            taskTitle.addStyleName("taskTitle");
            taskCard.addComponent(taskTitle);

            Label taskAssignee = new Label("Konrad Jażownik");
            taskTitle.addStyleName("taskAssignee");
            taskCard.addComponent(taskAssignee);

        }
    }


}
