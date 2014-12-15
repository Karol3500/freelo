package org.freelo.controller.users;

import com.vaadin.ui.Button;
import org.freelo.model.users.UserManagement;
import org.freelo.view.Register;
import org.freelo.view.SimpleLoginUI;

/**
 * Created by artur on 15.12.14.
 */
public class RegistrationController {
    private static final String USER_EXIST_NOTIFICATION = "User with given e-mail already exist.";
    private static final String INCORRECT_PASSWORDS_NOTIFICATION = "Passwords doesn't match.";
    private static final String WRONG_EMAIL_NOTIFICATION = "Wrong email format.";

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

            UserManagement user = new UserManagement();
            if (user.userAdd(registerView.getName(),registerView.getSurname(),registerView.getMail(),registerView.getPassword()) == null){
                registerView.setEmailExistError();
                return;
            }

            registerView.cleanTextFields();

            // todo information about registered user

        }
    }
}
