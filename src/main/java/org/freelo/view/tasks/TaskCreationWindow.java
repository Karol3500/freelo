package org.freelo.view.tasks;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.freelo.controller.tasks.TaskController;
import org.freelo.controller.tasks.TaskCreationController;
import org.freelo.view.SimpleLoginUI;

import java.util.List;

/**
 * Created by Konrad on 2014-12-13.
 */
public class TaskCreationWindow extends Window {

    private static final long serialVersionUID = 5678234591401040269L;

    TaskCreationController controller;

    VerticalLayout menu;
    TextField taskName;
    NativeSelect prioritySpinner;
    RichTextArea data;
    public List<CssLayout> columns;
    String userName;
    public Button createTaskButton;

    public TaskCreationWindow(List<CssLayout> columns, String userName) {
        //Appearance of the popup window
        super("Task Creation Window");
        this.columns = columns;
        this.userName = userName;
        setupPosition();
        setupPrioritySpinner();
        setupOtherComponents();
        setupMenu();
        controller = new TaskCreationController(this);
    }

    void setupMenu(){
        menu = new VerticalLayout();
        setContent(menu);
        menu.setSpacing(true);
        menu.addComponent(taskName);
        menu.addComponent(prioritySpinner);
        menu.addComponent(data);
        menu.addComponent(createTaskButton);
    }

    void setupOtherComponents(){
        taskName = new TextField();
        taskName.setInputPrompt("Name your Task");
        taskName.addStyleName("addTaskElements");

        data = new RichTextArea();
        data.setValue("Enter task description here...");

        createTaskButton = new Button("Create Task");
        createTaskButton.addStyleName("addTaskElements");
    }

    void setupPrioritySpinner(){
        prioritySpinner = new NativeSelect("  Task priority");
        prioritySpinner.setNullSelectionAllowed(false);
        prioritySpinner.addItem("High");
        prioritySpinner.addItem("Medium");
        prioritySpinner.addItem("Low");
        prioritySpinner.addStyleName("addTaskElements");
    }

    void setupPosition(){
        center();
        setWidth("450px");
        setPositionX(200);
        setPositionY(150);

    }

    public TaskCard createTask() {
        String task_data = data.getValue();
        String T_name = taskName.getValue();
        TaskCard tc = new TaskCard(T_name, prioritySpinner.getValue() != null ?
                                           prioritySpinner.getValue().toString() : "", task_data);
        tc.setColumns(columns);
        tc.creator = userName;
        tc.currentContainer = columns.get(0);
        tc.currentContainer.addComponent(tc);
        tc.taskList.add(tc);
        new TaskController(tc);
        return tc;
    }
}