package org.freelo.view.Dashboard;

import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.ProjectManagement.ProjectItem;

/**
 * Created by Konrad on 2015-01-13.
 */
public class PasswordSubwindow extends Window {
    private static final long serialVersionUID = 5678234591401040269L;

    private PasswordField newPasswordField, repeatPasswordField;

    UserManagement um = new UserManagement();
    User user;

    public PasswordSubwindow(final User user) {
        super("Change Password");
        //c = new CreateProjectSubwindowController(this);
        this.user = user;
        center();
        setClosable(true);
        setResizable(false);
        VerticalLayout main = new VerticalLayout();
        main.addStyleName("passwordpopup");
        main.setSizeFull();
        setContent(main);
        setHeight("250px");
        setWidth("400px");
        //setPositionY(50);
        //setPositionX(50);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, true, true, true));
        setContent(content);

        content.addComponent(buildPasswordField());
    }

    private Component buildPasswordField() {
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setSpacing(true);
        //root.setMargin(true);
        root.addStyleName("password-form");

        FormLayout passwordFields = new FormLayout();
        passwordFields.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(passwordFields);

        newPasswordField = new PasswordField("New Password:");
        newPasswordField.setWidth("100%");
        newPasswordField.setInputPrompt("Type New password:");
        newPasswordField.setNullRepresentation("");
        newPasswordField.setRequired(true);

        repeatPasswordField = new PasswordField("Repeat new Password");
        repeatPasswordField.setInputPrompt("Retype password..");
        repeatPasswordField.setWidth("100%");
        repeatPasswordField.setNullRepresentation("");
        repeatPasswordField.addValidator(new MatchValidator());

        passwordFields.addComponents(newPasswordField, repeatPasswordField);

        Button setButton = new Button("SET");
        setButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        setButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        setButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                repeatPasswordField.setValidationVisible(false);
                try {
                    repeatPasswordField.validate();
                    um.userUpdate(user);
                    Notification success = new Notification(
                            "Password updated successfully");
                    success.setDelayMsec(2000);
                    success.setStyleName("bar success small");
                    success.setPosition(Position.BOTTOM_CENTER);
                    success.show(Page.getCurrent());
                    close();
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                    repeatPasswordField.setValidationVisible(true);
                }
            }
        });
        setButton.focus();

        root.addComponent(setButton);
        root.setComponentAlignment(setButton, Alignment.MIDDLE_CENTER);
        return root;
    }

    class MatchValidator implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).equals(newPasswordField.getValue())))
                throw new InvalidValueException("Passwords don't match");
        }
    }


}
