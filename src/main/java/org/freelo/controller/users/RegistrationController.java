package org.freelo.controller.users;

import org.freelo.model.users.UserManagement;
import org.freelo.view.Register;

import com.vaadin.ui.Button;

/**
 * Created by artur on 15.12.14.
 */
public class RegistrationController {

    private Register registerView;

    public RegistrationController(Register registerUI){
        this.registerView = registerUI;
        setRegisterButtonListener();
    }

    public void setRegisterButtonListener(){
        registerView.RegisterMe.addClickListener(new registerButtonClickListenerEventHandler());
    }

    class registerButtonClickListenerEventHandler implements Button.ClickListener {
        private static final long serialVersionUID = -3960519875593438075L;

        @Override
        public void buttonClick(Button.ClickEvent event) {

            if (registerView.getMail().isEmpty() || registerView.getName().isEmpty() || registerView.getSurname().isEmpty() || registerView.getPassword().isEmpty()) return;

            if (!registerView.validateEmailPattern()) return;

            if (!registerView.validatePasswordPattern()) return;

            if (!registerView.validatePasswordConfirmation()){
                registerView.setConfirmationPasswordError();
                return;
            }

            if (UserManagement.userAdd(registerView.getName(),registerView.getSurname(),registerView.getMail(),registerView.getPassword()) == null){
                registerView.setEmailExistError();
                return;
            }

            registerView.cleanTextFields();

            // todo information about registered user
            registerView.registeredWindow();

        }
    }
}
