package org.freelo.view.tasks;


import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.freelo.model.sprints.Sprint;

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
    public VerticalLayout currentContainer;
    public Button rightButton;
    public Button leftButton;
    public String priorityString;
    public List<VerticalLayout> columns;
    public final ArrayList<TaskCard> taskList = new ArrayList<>();

    public TaskCard(final String name, String priorityString, final String data, final Sprint sprint) {
        taskName = name;
        taskNote = data;
        this.priorityString = priorityString;
        final VerticalLayout taskCard = new VerticalLayout();
        taskCard.addStyleName("task-card");
        taskCard.setWidth("97%");

        taskCard.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                Window CreateComponent = new TaskViewWindow(TaskCard.this, sprint);
                UI.getCurrent().addWindow(CreateComponent);
            }
        });

        leftButton = new Button("<");
        leftButton.addStyleName("switchButton");

        rightButton = new Button(">");
        rightButton.addStyleName("switchButton");

        final HorizontalLayout buttonsContainer = new HorizontalLayout();
        taskCard.addComponent(new Label(name, ContentMode.HTML));


        buttonsContainer.addComponent(leftButton);
        buttonsContainer.addComponent(rightButton);

        taskCard.addComponent(buttonsContainer);

        setSpacing(true);
        addComponent(taskCard);


        if (priorityString == "High") {
            taskCard.addStyleName("highprioritycard");
        }
        else if (priorityString == "Medium"){
            taskCard.addStyleName("mediumprioritycard");
            }

    }

    public void setColumns(List<VerticalLayout> c){
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