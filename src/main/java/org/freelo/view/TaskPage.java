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

        // the layout of task page is divided into 2 parts
        //containers
        //also used for popup windows
        final HorizontalLayout container = new HorizontalLayout();
        container.addStyleName("container");
        container.setWidth("100%");
        container.setHeight("100%");

//        final VerticalLayout container2 = new VerticalLayout();
//        container2.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//        container2.addStyleName("container2");
//        container2.setWidth("100%");
//        addComponent(container2);

        final VerticalLayout side_container = new VerticalLayout();
        side_container.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        side_container.addStyleName("side_container");


        // NEW TASK PAGE
        final Panel panel = new Panel("New Task Page");
        panel.addStyleName("panel");
        panel.setWidth("100%");

        // SIDE MENU
        Panel sidepanel = new Panel("Menu");
        sidepanel.addStyleName("sidepanel");
        sidepanel.setWidth("50%");

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

        // -------------------------------------
        container.addComponent(todopanel);
        container.addComponent(ongoingpanel);
        container.addComponent(donepanel);

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


        final Button addcomponent_button = new Button("Add Task");
        addcomponent_button.addStyleName("button1");
        addcomponent_button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749123339L;

            public void buttonClick(Button.ClickEvent event) {

                Window CreateComponent = new Subwindow(todo);
                UI.getCurrent().addWindow(CreateComponent);
            }
        });
        //adding elements to containers


        side_container.addComponent(addcomponent_button);
        side_container.addComponent(logout);
        sidepanel.setContent(side_container);
        addComponent(sidepanel);
        addComponent(container);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        String username = String.valueOf(getSession().getAttribute("user"));
        Notification welcome = new Notification("Welcome  " + username);
        welcome.setDelayMsec(30000);
        welcome.setPosition(Position.MIDDLE_CENTER);
        welcome.show(Page.getCurrent());

    }




}

