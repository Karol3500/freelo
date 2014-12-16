package org.freelo.view;


//import com.sun.javafx.tk.Toolkit;
import com.vaadin.data.Property;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.freelo.model.users.User;

/**
 * Created by Konrad on 2014-12-13.
 */
public class Subwindow extends Window {

    private static final long serialVersionUID = 5678234591401040269L;
    String priorityString;

    public Subwindow(final TaskCard tc) {
        super(tc.getTaskName() + " by " + tc.getUser());
        center();
        setHeight("300px");
        setWidth("400px");
        setPositionY(150);
        setPositionX(150);
        Button DeleteTask = new Button("Delete", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                deleteTask(tc.currentContainer, tc);
                close();
            }
        });
        VerticalLayout menu = new VerticalLayout();
        menu.setSizeFull();
        menu.setSpacing(true);
        Label TaskNotes = new Label(tc.getTaskNotes(), ContentMode.HTML);
        TaskNotes.setReadOnly(true);
        menu.addComponent(TaskNotes);
        menu.setComponentAlignment(TaskNotes, Alignment.MIDDLE_RIGHT);
        menu.addComponent(DeleteTask);
        menu.setComponentAlignment(DeleteTask, Alignment.BOTTOM_CENTER);
        this.setContent(menu);

    }

    public Subwindow(final CssLayout todo) {
        //Appearance of the popup window
        super("Task Creation Window");
        center();
        VerticalLayout menu = new VerticalLayout();
        //menu.setSizeFull();
        //menu.setSpacing(true);
        setContent(menu);
        //setHeight("650px");
        setWidth("450px");
        setPositionX(200);
        setPositionY(150);
        //---
        final TextField TaskName = new TextField();
        TaskName.setInputPrompt("Name your Task");

        final NativeSelect prioritySpinner = new NativeSelect("  Task priority");
        prioritySpinner.setNullSelectionAllowed(false);
        prioritySpinner.addItem("High");
        prioritySpinner.addItem("Medium");
        prioritySpinner.addItem("Low");

        prioritySpinner.addValueChangeListener(new ValueChangedListener());



        final RichTextArea data = new RichTextArea();
        data.setValue("Enter task description here...");

        final Button CreateTaskButton = new Button("Create Task", new Button.ClickListener() {
            private static final long serialVersionUID = -1181474151239122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                createTask(TaskName, priorityString, data, todo);
                close();
            }
        });

        //menu.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TaskName.addStyleName("addTaskElements");
        CreateTaskButton.addStyleName("addTaskElements");
        prioritySpinner.addStyleName("addTaskElements");

        menu.setSpacing(true);
        menu.addComponent(TaskName);
        menu.addComponent(prioritySpinner);
        menu.addComponent(data);
        menu.addComponent(CreateTaskButton);

    }

    private void createTask(final TextField TaskName, String priorityString, final RichTextArea data, final CssLayout todo) {
        TaskDataContainer TaskContainer = new TaskDataContainer();
        final String task_data = data.getValue();
        final String T_name = TaskName.getValue();
        TaskCard tc = new TaskCard(T_name, priorityString, task_data);
        tc.currentContainer = todo;
        todo.addComponent(tc);
        TaskContainer.addToArray(tc);
    }

    private void deleteTask(final CssLayout container, final TaskCard tc) {
        int itemindex = container.getComponentIndex(tc);
        Component todelete = container.getComponent(itemindex);
        container.removeComponent(todelete);
        TaskDataContainer TaskContainer = new TaskDataContainer();
        TaskContainer.delFromArray(tc);
    }

    class ValueChangedListener implements Property.ValueChangeListener {
        @Override
        public void valueChange(final Property.ValueChangeEvent event){
            priorityString = String.valueOf(event.getProperty()
                    .getValue());
        }
    }
}
