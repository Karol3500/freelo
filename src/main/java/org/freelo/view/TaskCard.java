package org.freelo.view;

import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;


/**
 * Created by Konrad on 2014-12-13.
 */

public class TaskCard extends VerticalLayout {
    private static final long serialVersionUID = 4924234591401040269L;
    public String gname;
    public String gdata;
    public User Creator = getUser();
    public TaskCard(final String name, final String data) {
        gname = name;
        gdata = data;
        final CssLayout taskCard = new CssLayout();
        taskCard.addStyleName("task-card");
        taskCard.setHeight("140px");
        taskCard.setWidth("200px");


        taskCard.addComponent(new Label(name, ContentMode.HTML));
        taskCard.addComponent(new Label(data, ContentMode.HTML));

        taskCard.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                Window CreateComponent = new Subwindow(getTaskName(), getTaskNotes(), Creator);
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
            taskCard.addComponent(leftButton);


        final Button rightButton = new Button(">");
        rightButton.addStyleName("switchButton");

        rightButton.addClickListener(new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {
                //method to
            }
        });
        taskCard.addComponent(rightButton);

        addComponent(taskCard);
    }

    public User getUser() {
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