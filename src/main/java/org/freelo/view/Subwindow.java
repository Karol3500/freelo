package org.freelo.view;


import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.freelo.model.users.User;

/**
 * Created by Konrad on 2014-12-13.
 */
public class Subwindow extends Window {

    private static final long serialVersionUID = 5678234591401040269L;

    public Subwindow(final String taskname, final String taskdata, final User Creator) {
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

    public Subwindow(final CssLayout todo) {
        //Appearance of the popup window
        super("Task Creation Window");
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
                createTask(TaskName, data, todo);
                close();
            }
        });

        menu.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        menu.setSpacing(true);
        menu.addComponent(TaskName);
        menu.addComponent(data);
        menu.addComponent(CreateTaskButton);

    }

    private void createTask(final TextField TaskName,final RichTextArea data, final CssLayout todo) {
        TaskDataContainer TaskContainer = new TaskDataContainer();
        final String task_data = data.getValue();
        final String T_name = TaskName.getValue();
        TaskCard tc = new TaskCard(T_name, task_data);
        todo.addComponent(tc);
        TaskContainer.addToArray(tc);
    }


}

