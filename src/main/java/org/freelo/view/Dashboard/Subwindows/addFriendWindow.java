package org.freelo.view.Dashboard.Subwindows;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Adrian on 20-01-2015.
 */
public class addFriendWindow extends Window {
    private static final long serialVersionUID = 5678234591401040269L;

    private String userName = null;
    public TextField friendField;
    public Button addFriendButton;

    public addFriendWindow() {
        super("Add User");
        center();
        setClosable(true);
        setResizable(false);
        VerticalLayout main = new VerticalLayout();
        main.addStyleName("userpopup");
        main.setSizeFull();
        setContent(main);
        setHeight("150px");
        setWidth("500px");

        main.addComponent(buildFriendField());
        //VerticalLayout content = new VerticalLayout();
        //content.setSizeFull();
        //content.setMargin(new MarginInfo(true, true, true, true));
        //setContent(content);

        //content.addComponent(buildUserField());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Component buildFriendField() {
        HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("user-form");

        friendField = new TextField();
        friendField.setInputPrompt("eg. joe@email.com");
        friendField.addValidator(new EmailValidator("Username must be an email!"));
        friendField.addValidator(new UserValidator());
        friendField.setWidth("100%");

        addFriendButton= new Button("+ Add Friend");
        addFriendButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addFriendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        addFriendButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                userName = null;
                friendField.setValidationVisible(false);
                try {
                    friendField.validate();
                    userName = friendField.getValue();
                    Notification success = new Notification(
                            "Friend added successfully");
                    success.setDelayMsec(1500);
                    success.show(Page.getCurrent());
                    close();
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                    friendField.setValidationVisible(true);
                }
            }
        });

        root.addComponents(friendField, addFriendButton);

        root.setExpandRatio(friendField, 2.0f);
        root.setExpandRatio(addFriendButton, 1.0f);

        return root;
    }

    class UserValidator implements Validator {
        private static final long serialVersionUID = 1L;

        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).equals(friendField.getValue())))
                throw new InvalidValueException("User: " + friendField.getValue()
                + " doesn't exists.");
        }
    }

}
