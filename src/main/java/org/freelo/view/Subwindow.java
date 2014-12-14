package org.freelo.view;

import com.sun.javafx.tk.Toolkit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.freelo.model.users.User;

/**
 * Created by Konrad on 2014-12-13.
 */
public class Subwindow extends Window {

    private static final long serialVersionUID = 5678234591401040269L;

    public Subwindow(final String taskname, final String taskdata, final String Creator) {
        super(taskname + " by " + Creator);
        center();
        setHeight("300px");
        setWidth("400px");
        setPositionY(150);
        setPositionX(150);
        VerticalLayout menu = new VerticalLayout();
        menu.setSizeFull();
        menu.setSpacing(true);
        Label TaskNotes = new Label(taskdata, ContentMode.HTML);
        TaskNotes.setReadOnly(true);
        menu.addComponent(TaskNotes);
        this.setContent(menu);

    }

    public Subwindow(final HorizontalLayout omg, final VerticalLayout sub) {
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
        final Button addcomponent_button = new Button("Add Task");
        addcomponent_button.addStyleName("button1");
        addcomponent_button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749123339L;

            public void buttonClick(Button.ClickEvent event) {

                Window CreateComponent = new Subwindow(todo);
                UI.getCurrent().addWindow(CreateComponent);
            }
        });

        final TextField ProjectName = new TextField("Enter project name");
        // todo.add date
        final Button CreateButton = new Button("Create", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                omg.addComponent(todopanel);
                omg.addComponent(ongoingpanel);
                omg.addComponent(donepanel);
                sub.addComponent(addcomponent_button);
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
        center();
        VerticalLayout menu = new VerticalLayout();
        menu.setSizeFull();
        menu.setSpacing(true);
        setContent(menu);
        setHeight("450px");
        setWidth("450px");
        setPositionX(200);
        setPositionY(150);
        //---
        final TextField TaskName = new TextField();
        TaskName.setInputPrompt("Name your Task");

        final RichTextArea data = new RichTextArea();

        final Button CreateTaskButton = new Button("Create Task", new Button.ClickListener() {
            private static final long serialVersionUID = -1181474151239122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final String task_data = data.getValue();
                final String T_name = TaskName.getValue();
                TaskCard tc = new TaskCard(T_name, task_data);
                todo.addComponent(tc);
                close();
            }
        });
        menu.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        menu.setSpacing(true);
        menu.addComponent(TaskName);
        menu.addComponent(data);
        menu.addComponent(CreateTaskButton);

    }


}

