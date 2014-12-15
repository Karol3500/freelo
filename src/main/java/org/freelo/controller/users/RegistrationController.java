package org.freelo.controller.users;

import com.vaadin.ui.Button;
import org.freelo.view.Register;
import org.freelo.view.SimpleLoginUI;

/**
 * Created by artur on 15.12.14.
 */
public class RegistrationController {
    private static final String NO_SUCH_USER_NOTIFICATION = "User with given e-mail doesn't exist.";
    private static final String INCORRECT_PASSWORD_NOTIFICATION = "Incorrect password";

    private Register registerUI;

    public RegistrationController(Register registerUI){
        this.registerUI = registerUI;
        setRegisterButtonListener();
    }

    public void setRegisterButtonListener(){
        registerUI.RegisterMe.addClickListener(new registerButtonClickListenerEventHandler());
    }

    class registerButtonClickListenerEventHandler implements Button.ClickListener {
        private static final long serialVersionUID = -3960519875593438075L;

        @Override
        public void buttonClick(Button.ClickEvent event) {

            System.out.println("regcontroler.java");


        }
    }
}
