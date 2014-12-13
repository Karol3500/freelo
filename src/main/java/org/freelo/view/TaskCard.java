package org.freelo.view;

import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.freelo.model.users.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

/**
 * Created by Konrad on 2014-12-13.
 */
public class TaskCard extends VerticalLayout {
    private static final long serialVersionUID = 4924234591401040269L;
    public String gname;
    public String gdata;
    public TaskCard(final String name, final String data) {
        gname = name;
        gdata = data;
        final CssLayout taskCard = new CssLayout();
        taskCard.addStyleName("task-card");
        taskCard.setHeight("140px");
        taskCard.setWidth("200px");
        addComponent(taskCard);

        taskCard.addComponent(new Label(name, ContentMode.HTML));
        taskCard.addComponent(new Label(data, ContentMode.HTML));

        taskCard.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                Window CreateComponent = new Subwindow(getTaskName(), getTaskNotes());
                UI.getCurrent().addWindow(CreateComponent);
            }
        });
        final Button claimButton = new Button("Claim");
        claimButton.addStyleName("claimButton");
            /*
            claimButton.addClickListener(new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {
            todo.removeComponent(new TaskCard());
                }
           });
            taskCard.addComponent(claimButton);
        */
    }

    public String getCreatorName() {
        String username = String.valueOf(getSession().getAttribute("user"));
        return username;
    }

    public String getTaskName() {
        return gname;
    }

    public String getTaskNotes() {
        return gdata;
    }


}