package org.freelo.view;


import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.freelo.model.users.User;
import java.util.ArrayList;


/**
 * Created by Konrad on 2014-12-13.
 */

public class TaskCard extends VerticalLayout {
    private static final long serialVersionUID = 4924234591401040269L;
    public String gname;
    public String gdata;
    public String Creator = getUser();
    public CssLayout currentContainer;

    public TaskCard(final String name, final String data) {
        gname = name;
        gdata = data;
        final CssLayout taskCard = new CssLayout();
        taskCard.addStyleName("task-card");
        taskCard.setHeight("120px");
        taskCard.setWidth("180px");

        taskCard.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                Window CreateComponent = new Subwindow(TaskCard.this);
                UI.getCurrent().addWindow(CreateComponent);
            }
        });

        final Button leftButton = new Button("<");
        leftButton.addStyleName("switchButton");
        leftButton.addClickListener(new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {
            //method to
                }
           });

        final Button rightButton = new Button(">");
        rightButton.addStyleName("switchButton");
        rightButton.addClickListener(new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {
                //method to
            }
        });


        taskCard.addComponent(new Label(name, ContentMode.HTML));
        taskCard.addComponent(leftButton);
        taskCard.addComponent(rightButton);
        setSpacing(true);
        addComponent(taskCard);
    }

    public String getUser() {
//        String username = String.valueOf(getSession().getAttribute("user"));
//        User usrId = UserManagement.getUser(username);
        return null;
    }

    public String getTaskName() {
        return gname;
    }

    public String getTaskNotes() {
        return gdata;
    }

}

class TaskDataContainer {
    private ArrayList<TaskCard> TaskList = new ArrayList<>();
    public TaskDataContainer() {

    }
    public void addToArray(final TaskCard tc) {
        int size = TaskList.size();
        TaskList.add(size, tc);
    }
}