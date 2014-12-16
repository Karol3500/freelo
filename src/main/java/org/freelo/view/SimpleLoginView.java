package org.freelo.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.ValoTheme;
import javafx.scene.input.KeyCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;


@Component
@Scope("prototype")
public class SimpleLoginView extends VerticalLayout implements View,
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
	
	private TextField textFieldUser;

	private PasswordField textFieldPassword;

	public Button loginButton;

	public Button registerButton;

    //Method checking if user is enrolled to be implemented - boolean hardcoded to false to display project management page after login
    //public static boolean isAssigned = false;

	public SimpleLoginView() {
		setSizeFull();

        com.vaadin.ui.Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

    }

    private com.vaadin.ui.Component buildLoginForm(){
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setMargin(new MarginInfo(true, true, true, true));
        loginPanel.setSpacing(true);
        loginPanel.setStyleName(ValoTheme.LAYOUT_CARD);
        Responsive.makeResponsive(loginPanel);
        //loginPanel.addStyleName("login-panel");

        com.vaadin.ui.Component labels = buildLabels();

        loginPanel.addComponent(labels);
        loginPanel.addComponent(buildFields());
        loginPanel.addComponent(buildButtons());

        loginPanel.setComponentAlignment(labels, Alignment.MIDDLE_CENTER);
        //loginPanel.addComponent(new CheckBox("Remember me", true));
        return loginPanel;
    }

    private com.vaadin.ui.Component buildFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        // Create the user input field
        textFieldUser = new TextField("User:");
        textFieldUser.setWidth("300px");
        textFieldUser.setIcon(FontAwesome.USER);
        textFieldUser.setRequired(true);
        textFieldUser.setInputPrompt("Your username (eg. joe@email.com)");
        textFieldUser.setNullRepresentation("");
        textFieldUser.addValidator(new EmailValidator(
                "Username must be an email address"));
        textFieldUser.setInvalidAllowed(false);
        textFieldUser.setImmediate(true);

        // Create the password input field
        textFieldPassword = new PasswordField("Password:");
        textFieldPassword.setIcon(FontAwesome.LOCK);
        textFieldPassword.setWidth("300px");
        textFieldPassword.addValidator(new PasswordValidator());
        textFieldPassword.setRequired(true);
        textFieldPassword.setValue("");
        textFieldPassword.setNullRepresentation("");
        textFieldPassword.setInputPrompt("Password");
        textFieldPassword.setImmediate(true);

        fields.addComponents(textFieldUser, textFieldPassword);

        return fields;
    }

    private com.vaadin.ui.Component buildButtons() {
        VerticalLayout buttons = new VerticalLayout();
        buttons.setSpacing(true);
        buttons.addStyleName("fields");
        buttons.setSizeFull();

        // Create login button
        loginButton = new Button("Login");
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginButton.addClickListener(this);
        loginButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        // Create register button
        Label registerLabel = new Label("Not registered?");
        registerLabel.setStyleName(ValoTheme.LABEL_SMALL);
        Label gap = new Label();
        gap.setHeight("1em");

        registerButton = new Button("Register Here", new Button.ClickListener() {

            private static final long serialVersionUID = -7173240164416449514L;

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getNavigator().navigateTo(Register.NAME);
            }
        });
        registerButton.addStyleName(BaseTheme.BUTTON_LINK);

        buttons.addComponents(loginButton, gap, registerLabel, registerButton);
        return buttons;

    }

    private com.vaadin.ui.Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Welcome Into FREELO World");
        welcome.setWidthUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H2);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        welcome.addStyleName(ValoTheme.LABEL_BOLD);

        labels.addComponent(welcome);

        /*
        Label title = new Label("Please provide username in email form (username@freelo.com)");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        labels.addComponent(title);
        */

        return labels;
    }

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		// focus the username field when user arrives to the login view
        textFieldUser.setValue("");
        textFieldPassword.setValue("");
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

        //if (!isAssigned) {
            getUI().getNavigator().navigateTo(ProjectManagementPage.NAME);
        //getUI().getNavigator().navigateTo(MainView.NAME);
        //}
        //else {
        //    getUI().getNavigator().navigateTo(TaskPage.NAME);
        //}

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

	public String getUsername(){
		return textFieldUser.getValue();
	}

	public String getPassword(){
		return textFieldPassword.getValue();
	}
}