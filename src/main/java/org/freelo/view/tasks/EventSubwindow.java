package org.freelo.view.tasks;

import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;

/**
 * Created by Konrad on 2015-01-13.
 */
public class EventSubwindow extends Window {
    private static final long serialVersionUID = 5678234591401040269L;

    private TextField eventNameField, eventDescriptionName;
    DateField dateField;
    TaskPage tp;

    public EventSubwindow(TaskPage tp) {
        super("Create an Event");
        this.tp = tp;

        center();
        setClosable(true);
        setResizable(false);
        VerticalLayout main = new VerticalLayout();
        main.addStyleName("passwordpopup");
        main.setSizeFull();
        setContent(main);
        setHeight("250px");
        setWidth("400px");

        main.addComponent(buildEventFields());
    }

    private Component buildEventFields() {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setSpacing(true);
        root.setMargin(new MarginInfo(false, true, false, true));
        root.addStyleName("event-form");

        FormLayout eventFields = new FormLayout();
        eventFields.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(eventFields);

        eventNameField = new TextField("Name");
        eventNameField.setWidth("70%");
        eventNameField.setInputPrompt(". . .");
        eventNameField.setNullRepresentation("");

        eventDescriptionName = new TextField("Description");
        eventDescriptionName.setWidth("100%");
        eventDescriptionName.setInputPrompt(". . .");
        eventDescriptionName.setNullRepresentation("");

        dateField = new DateField("Event Date");
        dateField.setValue(new Date());
        dateField.setResolution(DateField.RESOLUTION_MIN);
        eventFields.addComponents(eventNameField, eventDescriptionName, dateField);

        Button saveButton = new Button("ADD");
        saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        saveButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
            public void buttonClick(Button.ClickEvent event) {
                tp.addEvent(eventNameField.getValue(), eventDescriptionName.getValue(), dateField.getValue(), false);
                close();
            }
        });

        root.addComponent(saveButton);
        root.setComponentAlignment(saveButton, Alignment.MIDDLE_CENTER);
        return root;
    }
}
