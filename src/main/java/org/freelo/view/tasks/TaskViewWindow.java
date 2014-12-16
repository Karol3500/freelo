package org.freelo.view.tasks;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.freelo.controller.tasks.TaskViewController;

/**
 * Created by karol on 16.12.14.
 */
public class TaskViewWindow extends Window {
    TaskViewController controller;
    public TaskCard tc;
    public Button deleteTaskButton;
    VerticalLayout menu;
    Label taskNote;

    public TaskViewWindow(TaskCard tc) {
        super(tc.getTaskName() + " by " + tc.getUser());
        this.tc = tc;
        deleteTaskButton = new Button("Delete");
        setupPosition();
        setupTaskNoteLabel(tc);
        setupMenu();
        this.setContent(menu);
        controller = new TaskViewController(this);
    }
    void setupPosition(){
        center();
        setHeight("300px");
        setWidth("400px");
        setPositionY(150);
        setPositionX(150);
    }
    void setupMenu(){
        menu = new VerticalLayout();
        menu.setSizeFull();
        menu.setSpacing(true);
        menu.addComponent(taskNote);
        menu.setComponentAlignment(taskNote, Alignment.MIDDLE_RIGHT);
        menu.addComponent(deleteTaskButton);
        menu.setComponentAlignment(deleteTaskButton, Alignment.BOTTOM_CENTER);
    }

    void setupTaskNoteLabel(TaskCard tc){
        taskNote = new Label(tc.getTaskNote(), ContentMode.HTML);
        taskNote.setReadOnly(true);
    }

    public void deleteTask() {
        tc.taskList.remove(tc);
        tc.currentContainer.removeComponent(tc);
        close();
    }
}
