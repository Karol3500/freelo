package org.freelo.view;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Konrad on 2014-11-16.
 */
@Component
@Scope("prototype")
public class Register extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = 645144581199267137L;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "^.{8,}$";
    private Pattern loginPattern = Pattern.compile(EMAIL_PATTERN);
    private Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

	public static final String NAME = "Registration";
    protected final PasswordField password = new PasswordField("Password");
    protected final Label title = new Label("Registration");
    protected final TextField username = new TextField("Username");

    protected final TextField mail = new TextField("E-mail Address");
    protected final TextField name = new TextField("Name");
    protected final TextField surname = new TextField("Surname");

    Button RegisterMe;
    Button BackButton;

    public Register(){
        password.isRequired();
        password.addValidator(new PasswordValidator());
        username.isRequired();
        mail.isRequired();
        mail.addValidator(new EmailValidator("Wrong mail format !"));

        setSizeFull();
        BackButton = new Button("Back", new Button.ClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -2456226905721789089L;

			@Override
        public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getNavigator().navigateTo(SimpleLoginView.NAME);

            }
        });
        RegisterMe = new Button("Proceed", new Button.ClickListener() {
            /**
			 * 
			 */
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
//                boolean password_status = check_the_password();
                if(!validateEMAILandPassword()) {
                    getUI().getNavigator().navigateTo(NAME);

                } else {
                    getUI().getNavigator().navigateTo(SimpleLoginView.NAME);
                    welcome.show(Page.getCurrent());
                }


            }

        });

        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        VerticalLayout mainregpage = new VerticalLayout();
        mainregpage.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainregpage.setSpacing(true);
        mainregpage.addComponent(title);
        mainregpage.addComponent(username);
        mainregpage.addComponent(password);
        mainregpage.addComponent(mail);
        mainregpage.addComponent(name);
        mainregpage.addComponent(surname);
        mainregpage.addComponent(RegisterMe);
        mainregpage.addComponent(BackButton);
        mainregpage.setMargin(new MarginInfo(true, true, true, true));
        addComponent(mainregpage);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        username.focus();
    }

    protected String[] RegisterNewUser() {
        String[] user_data = {username.getValue(), password.getValue(), mail.getValue(), name.getValue(), surname.getValue()};
        return user_data;
    }

    private static final class PasswordValidator extends
            AbstractValidator<String> {

        /**
         *
         */
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
    }
    private boolean validateEmail(){

        Matcher m = loginPattern.matcher(mail.getValue());
        if(m.matches()){
            return true;
        }
        return false;
    }

    private boolean validatePassword(){
        Matcher m = passwordPattern.matcher(password.getValue());
        if(m.matches()){
            return true;
        }
        return false;
    }
    public boolean validateEMAILandPassword() {
        if(validateEmail()&&validatePassword()) {
            return true;
        }
        return false;
    }

}
