package org.freelo.view.Dashboard.Subwindows;

import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;

import com.vaadin.data.Validator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import javax.xml.bind.ValidationException;

/**
 * Created by Konrad on 2015-01-13.
 */
public class PasswordSubwindow extends Window {
    private static final long serialVersionUID = 5678234591401040269L;

    private PasswordField oldPasswordField, newPasswordField, repeatPasswordField;

    UserManagement um = new UserManagement();
    User user;

    public PasswordSubwindow(final User user) {
        super("Change Password");
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
        root.addStyleName("password-form");

        FormLayout passwordFields = new FormLayout();
        passwordFields.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(passwordFields);

        oldPasswordField = new PasswordField("Current Password:");
        oldPasswordField.setWidth("100%");
        oldPasswordField.setInputPrompt("password..");
        oldPasswordField.setNullRepresentation("");
        oldPasswordField.setRequired(true);
        oldPasswordField.addValidator(new CurrentPasswordValidator());

        newPasswordField = new PasswordField("New Password:");
        newPasswordField.setWidth("100%");
        newPasswordField.setInputPrompt("password..");
        newPasswordField.setNullRepresentation("");
        newPasswordField.setRequired(true);

        repeatPasswordField = new PasswordField("Repeat New Password");
        repeatPasswordField.setInputPrompt("password..");
        repeatPasswordField.setWidth("100%");
        repeatPasswordField.setNullRepresentation("");
        repeatPasswordField.addValidator(new MatchValidator());
        repeatPasswordField.addValidator(new EmptyFieldValidator());
        repeatPasswordField.setValidationVisible(false);

        passwordFields.addComponents(oldPasswordField, newPasswordField, repeatPasswordField);

        Button setButton = new Button("SET");
        setButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        setButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        setButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
            public void buttonClick(Button.ClickEvent event) {
                repeatPasswordField.setValidationVisible(false);
                try {
                    oldPasswordField.validate();
                    repeatPasswordField.validate();
                    UserManagement.userUpdate(user);
                    Notification success = new Notification(
                            "Password updated successfully");
                    success.setDelayMsec(1500);
                    success.show(Page.getCurrent());
                    close();
                } catch (Validator.EmptyValueException e ){
                    Notification.show(e.getMessage());
                    repeatPasswordField.setValidationVisible(true);
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
        private static final long serialVersionUID = 1L;

        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).equals(newPasswordField.getValue())))
                throw new InvalidValueException("Passwords don't match");
        }
    }

    class EmptyFieldValidator implements Validator {
        @Override
        public void validate(Object value)
                throws EmptyValueException {
            if (((String)value).isEmpty())
                throw new EmptyValueException("Password field cannot be empty");
        }
    }

    class CurrentPasswordValidator implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).equals(user.getPassword())))
                throw new InvalidValueException("Current password is incorrect");
        }
    }


}
