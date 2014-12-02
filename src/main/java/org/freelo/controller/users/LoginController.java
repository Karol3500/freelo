package org.freelo.controller.users;

import javax.annotation.PostConstruct;

import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.SimpleLoginUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Button;

/**
 * Created by karol on 02.12.14.
 */
@Component
@Scope("prototype")
public class LoginController {
	private static final String NO_SUCH_USER_NOTIFICATION = "User with given e-mail doesn't exist.";
	private static final String INCORRECT_PASSWORD_NOTIFICATION = "Incorrect password";
	
    @Autowired
    private SimpleLoginUI loginUI;

    @PostConstruct
    public void setLoginButtonListener(){
    	loginUI.loginView.loginButton.addClickListener(new loginButtonClickListenerEventHandler());
    }
    
    class loginButtonClickListenerEventHandler implements Button.ClickListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8080776772321261004L;
		private String userName;
        private String password;
        @Override
        public void buttonClick(Button.ClickEvent event) {
        	if(!loginUI.loginView.validateLoginAndPassword())
        		return;
        	User user = UserManagement.getUserByMail(userName);
        	if(user == null){
        		loginUI.loginView.showNotification(NO_SUCH_USER_NOTIFICATION);
        		return;	
        	}
        	if(!user.getPassword().equals(password)){
        		loginUI.loginView.showNotification(INCORRECT_PASSWORD_NOTIFICATION);
        		return;	
        	}
        	loginUI.loginView.storeCurrentUserInServiceSession();
        	loginUI.loginView.navigateToMainView();
        }
    }
}
