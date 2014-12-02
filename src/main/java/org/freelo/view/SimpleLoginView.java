package org.freelo.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;


@Component
@Scope("prototype")
public class SimpleLoginView extends CustomComponent implements View,
Button.ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4241677791091569418L;

	public static final String NAME = "login";
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PASSWORD_PATTERN = "^.{8,}$";

	private Pattern loginPattern = Pattern.compile(EMAIL_PATTERN);
	private Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
	
	private final TextField textFieldUser;

	private final PasswordField textFieldPassword;

	public final Button loginButton;

	public final Button registerButton;

	public SimpleLoginView() {
		setSizeFull();

		// Create the user input field
		textFieldUser = new TextField("User:");
		textFieldUser.setWidth("300px");
		textFieldUser.setRequired(true);
		textFieldUser.setInputPrompt("Your username (eg. joe@email.com)");
		textFieldUser.addValidator(new EmailValidator(
				"Username must be an email address"));
		textFieldUser.setInvalidAllowed(false);

		// Create the password input field
		textFieldPassword = new PasswordField("Password:");
		textFieldPassword.setWidth("300px");
		textFieldPassword.addValidator(new PasswordValidator());
		textFieldPassword.setRequired(true);
		textFieldPassword.setValue("");
		textFieldPassword.setNullRepresentation("");

		// Create login button
		loginButton = new Button("Login");
		loginButton.addClickListener(this);
		// Create register button
		registerButton = new Button("Register", new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7173240164416449514L;

			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				getUI().getNavigator().navigateTo(Register.NAME);
			}
		});
		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(textFieldUser, textFieldPassword, loginButton, registerButton);
		fields.setCaption("Please login to access the application. (test@test.com/passw0rd)");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, true));
		fields.setSizeUndefined();

		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		// focus the username field when user arrives to the login view
		textFieldUser.focus();
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

	private boolean validateLogin(){
		
		Matcher m = loginPattern.matcher(textFieldUser.getValue());
		if(m.matches()){
			return true;
		}
		return false;
	}
	
	private boolean validatePassword(){
		Matcher m = passwordPattern.matcher(textFieldPassword.getValue());
		if(m.matches()){
			return true;
		}
		return false;
	}
	
	public boolean validateLoginAndPassword(){
		if(validateLogin()&&validatePassword()){
			return true;
		}
		return false;
	}

	public void storeCurrentUserInServiceSession(){
		String username = textFieldUser.getValue();
		getSession().setAttribute("user", username);
	}
	
	public void navigateToMainView(){
		getUI().getNavigator().navigateTo(TaskPage.NAME);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(!validateLogin()){
			textFieldUser.setValue(null);
			textFieldUser.focus();
		}
		if(!validatePassword()){
			this.textFieldPassword.setValue(null);
			this.textFieldPassword.focus();
		}
	}
	
	public void showNotification(String n){
		Notification.show(n);
	}
}