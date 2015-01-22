package org.freelo.controller.users;

import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.Dashboard.DashboardMenuBean;
import org.freelo.view.SimpleLoginUI;

import com.vaadin.ui.Button;

/**
 * Created by karol on 02.12.14.
 */

public class LoginController {
	private static final String NO_SUCH_USER_NOTIFICATION = "User with given e-mail doesn't exist.";
	private static final String INCORRECT_PASSWORD_NOTIFICATION = "Incorrect password";

    DashboardMenuBean dashboardMenuBean;

    private SimpleLoginUI loginUI;

	public LoginController(SimpleLoginUI loginUI, DashboardMenuBean dashboardMenuBean){
        this.dashboardMenuBean = dashboardMenuBean;
		this.loginUI = loginUI;
		setLoginButtonListener();
	}

    public void setLoginButtonListener(){
    	loginUI.loginView.loginButton.addClickListener(new loginButtonClickListenerEventHandler());
    }
    
    class loginButtonClickListenerEventHandler implements Button.ClickListener {
		private static final long serialVersionUID = -8080776772321261004L;
		private String userName;
        private String password;
        @Override
        public void buttonClick(Button.ClickEvent event) {
        	if(!loginUI.loginView.validateLoginAndPassword())
        		return;
			userName = loginUI.loginView.getUsername();
			password = loginUI.loginView.getPassword();

        	User user = UserManagement.getUser(userName);
        	if(user == null){
        		loginUI.loginView.showNotification(NO_SUCH_USER_NOTIFICATION);
        		return;	
        	}
        	if(!user.getPassword().equals(password)){
        		loginUI.loginView.showNotification(INCORRECT_PASSWORD_NOTIFICATION);
        		return;	
        	}
        	loginUI.loginView.storeCurrentUserInServiceSession();
            loginUI.loginView.storeCurrentUserInServiceSession(user);
            dashboardMenuBean.initUIs(loginUI);
			loginUI.projectManagementView.pageController.populateProjectPageAfterLogin(loginUI.loginView.getUsername());
        	loginUI.loginView.navigateToMainView();
        }
    }
}
