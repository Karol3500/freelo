package org.freelo.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Konrad on 2014-12-13.
 */
public class TaskCard extends VerticalLayout {
    private static final long serialVersionUID = 4924234591401040269L;

    public TaskCard(String name, String data) {
        final CssLayout taskCard = new CssLayout();
        taskCard.addStyleName("task-card");
        taskCard.setHeight("100px");
        addComponent(taskCard);

        taskCard.addComponent(new Label(name));
        taskCard.addComponent(new Label(data));

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


}