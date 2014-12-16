package org.freelo.view.tasks;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import org.freelo.view.Dashboard.DashboardMenu;
import org.freelo.view.Dashboard.DashboardMenuBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.vaadin.ui.Window;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jan on 2014-11-08.
 */
@Component
@Scope("prototype")
public class TaskPage extends HorizontalLayout implements View {

    private static final long serialVersionUID = -906274928780939032L;
    public static final String NAME = "";

    public List<CssLayout> columns;

    final HorizontalLayout container = new HorizontalLayout();
    final HorizontalLayout taskPanelContainer = new HorizontalLayout();

    final Panel todopanel = new Panel("TODO");
    final CssLayout todo = new CssLayout();

    final Panel ongoingpanel = new Panel("ON GOING");
    final CssLayout ongoing = new CssLayout();

    final Panel donepanel = new Panel("DONE");
    final CssLayout done = new CssLayout();

    DashboardMenu dashBoard;

    @Autowired
    DashboardMenuBean dashboardMenuBean;

    public TaskPage() {
        setSizeFull();
        addStyleName("taskpage");

        columns = new ArrayList<CssLayout>();

        container.addStyleName("container");
        container.setHeight("100%");
        addComponent(container);

        taskPanelContainer.setHeight("100%");
        taskPanelContainer.setWidth("1000px");


        todo.addStyleName("content");
        todopanel.addStyleName("todopanel");
        todopanel.setHeight("100%");
        todopanel.setContent(todo);

        // ONGOING
        ongoing.addStyleName("content");
        ongoingpanel.addStyleName("ongoingpanel");
        ongoingpanel.setHeight("100%");
        ongoingpanel.setContent(ongoing);

        // DONE
        done.addStyleName("content");
        donepanel.addStyleName("donepanel");
        donepanel.setHeight("100%");
        donepanel.setContent(done);
    }

    @PostConstruct
    private void setup(){
        taskPanelContainer.addComponent(todopanel);
        taskPanelContainer.addComponent(ongoingpanel);
        taskPanelContainer.addComponent(donepanel);

        columns.add(todo);
        columns.add(ongoing);
        columns.add(done);

        dashBoard = dashboardMenuBean.getNewDashboardMenu();
        final Button addComponentButton = new Button("Add Task");
        addComponentButton.addStyleName("button1");
        addComponentButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749123339L;

            public void buttonClick(Button.ClickEvent event) {
                TaskCreationWindow taskCreationWindow=
                        new TaskCreationWindow(columns, dashBoard.ui.getSession().getAttribute("user").toString());
                UI.getCurrent().addWindow(taskCreationWindow);
            }
        });
        //adding elements to containers

        todo.addComponent(addComponentButton);
        container.addComponent(dashBoard);
        container.addComponent(taskPanelContainer);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }




}

