package org.freelo.view.tasks;


import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.freelo.controller.tasks.TaskController;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Konrad on 2014-12-13.
 */

public class TaskCard extends VerticalLayout {
    private static final long serialVersionUID = 4924234591401040269L;
    public String taskName;
    public String taskNote;
    public String creator;
    public CssLayout currentContainer;
    public Button rightButton;
    public Button leftButton;
    public String priorityString;
    public List<CssLayout> columns;
    public final ArrayList<TaskCard> taskList = new ArrayList<>();

    public TaskCard(final String name, String priorityString, final String data) {
        taskName = name;
        taskNote = data;
        this.priorityString = priorityString;
        final CssLayout taskCard = new CssLayout();
        taskCard.addStyleName("task-card");
        taskCard.setWidth("97%");

        taskCard.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                Window CreateComponent = new TaskViewWindow(TaskCard.this);
                UI.getCurrent().addWindow(CreateComponent);
            }
        });

        leftButton = new Button("<");
        leftButton.addStyleName("switchButton");

        rightButton = new Button(">");
        rightButton.addStyleName("switchButton");


        taskCard.addComponent(new Label(name, ContentMode.HTML));
        taskCard.addComponent(leftButton);
        taskCard.addComponent(rightButton);
        setSpacing(true);
        addComponent(taskCard);


        if (priorityString == "High") {
            taskCard.addStyleName("highprioritycard");
        }
        else if (priorityString == "Medium"){
            taskCard.addStyleName("mediumprioritycard");
            }

    }

    public void setColumns(List<CssLayout> c){
        this.columns = c;
    }

    public String getUser() {
        String username = String.valueOf(getSession().getAttribute("user"));
        return username;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskNote() {
        return taskNote;
    }

}