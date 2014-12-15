package org.freelo.view;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.UserError;
import org.freelo.controller.users.RegistrationController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;


/**
 * Created by Konrad on 2014-11-16.
 */
@Component
@Scope("prototype")
public class Register extends VerticalLayout implements View {

	private static final long serialVersionUID = 645144581199267137L;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "^.{8,}$";
    private Pattern loginPattern = Pattern.compile(EMAIL_PATTERN);
    private Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

	public static final String NAME = "Registration";
    protected final Label title = new Label("Registration");

    protected final PasswordField password = new PasswordField("Password");
    protected final PasswordField passwordConfirmation = new PasswordField("Confirm password");
    protected final TextField mail = new TextField("E-mail Address");
    protected final TextField name = new TextField("Name");
    protected final TextField surname = new TextField("Surname");

    public Button RegisterMe;
    private Button BackButton;

    public RegistrationController registerController;
    @PostConstruct
    void setupController(){ registerController = new RegistrationController(this); }

    public Register(){
        password.setRequired(true);
        password.addValidator(new PasswordValidator("Password must have minimum 8 characters."));
        passwordConfirmation.setRequired(true);
        //passwordConfirmation.setComponentError(new UserError("Passwords must be the same."));
        //passwordConfirmation.addValidator(new ConfirmPasswordValidator());
        name.setRequired(true);
        surname.setRequired(true);
        mail.setRequired(true);
        mail.addValidator(new EmailValidator("Wrong mail format !"));

        setSizeFull();
        BackButton = new Button("Back", new Button.ClickListener() {
			private static final long serialVersionUID = -2456226905721789089L;
			@Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                cleanTextFields();
                getUI().getNavigator().navigateTo(SimpleLoginView.NAME);
            }
        });

        RegisterMe = new Button("Register");
        // below code is moved to controller
        /*RegisterMe = new Button("Proceed", new Button.ClickListener() {
			private static final long serialVersionUID = -3960519875593438075L;

			@Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String username = mail.getValue();
                //Add some controller handler to obtain data from method
                RegisterNewUser();
                //
                Notification welcome = new Notification(username + " registered !");
                welcome.setDelayMsec(20000);
                welcome.setPosition(Position.MIDDLE_CENTER);
                if(!validateEMAILandPassword()) {
                    getUI().getNavigator().navigateTo(NAME);
                } else {
                    getUI().getNavigator().navigateTo(SimpleLoginView.NAME);
                    welcome.show(Page.getCurrent());
                }
                System.out.println("register.java");
            }
        });*/

        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        VerticalLayout mainregpage = new VerticalLayout();
        mainregpage.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainregpage.setSpacing(true);
        //mainregpage.addComponent(title);
        mainregpage.addComponent(mail);
        mainregpage.addComponent(name);
        mainregpage.addComponent(surname);
        mainregpage.addComponent(password);
        mainregpage.addComponent(passwordConfirmation);
        mainregpage.addComponent(RegisterMe);
        mainregpage.addComponent(BackButton);
        mainregpage.setMargin(new MarginInfo(true, true, true, true));
        addComponent(mainregpage);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        mail.focus();
    }

    public String getPassword() { return password.getValue(); }

    public String getMail() { return mail.getValue(); }

    public String getName() { return name.getValue(); }

    public String getSurname() { return surname.getValue(); }

    public boolean validateEmailPattern(){ return loginPattern.matcher(mail.getValue()).matches(); }

    public boolean validatePasswordPattern(){ return passwordPattern.matcher(password.getValue()).matches(); }

    public boolean validatePasswordConfirmation(){ return password.getValue().equals(passwordConfirmation.getValue()); }

    public void cleanTextFields(){
        mail.setValue("");
        name.setValue("");
        surname.setValue("");
        password.setValue("");
        passwordConfirmation.setValue("");
    }

    public static class PasswordValidator extends RegexpValidator {
        public PasswordValidator(String errorMessage) {
            super(PASSWORD_PATTERN, true, errorMessage);
        }
    }

    // I dont know how to do it in different way /Artur
    public void setEmailExistError() { mail.setComponentError(new UserError("User already exist.")); }
    public void setConfirmationPasswordError() { passwordConfirmation.setComponentError(new UserError("Passwords must be the same.")); }


    /*public class ConfirmPasswordValidator extends UserError {
        @Override
        public ConfirmPasswordValidator(String errorMessage) {
            //super(PASSWORD_PATTERN, true, errorMessage);
        }
    }*/


    // old version
    /*private static final class PasswordValidator extends
            AbstractValidator<String> {
        private static final long serialVersionUID = -5461407030697310024L;

        public PasswordValidator() {
            super("The password provided is not valid");
        }

        @Override
        protected boolean isValidValue(String value) {
            //
            // Password must be at least 8 characters long and contain at least
            // one number
            //
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher m = pattern.matcher(value);
            if(m.matches()){
                return true;
            }
            return false;
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }*/


}
