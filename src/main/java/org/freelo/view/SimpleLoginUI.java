package org.freelo.view;

import com.vaadin.server.VaadinSession;
import org.freelo.controller.users.LoginController;
import org.freelo.model.users.User;
import org.freelo.view.Dashboard.DashboardMenu;
import org.freelo.view.Dashboard.DashboardMenuBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.spring.VaadinUI;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by Konrad on 2014-11-17.
 */
@Theme("themefreelo")
@VaadinUI
@Component
@Scope("prototype")
public class SimpleLoginUI extends UI {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3048506907544622091L;
    @Autowired
    DashboardMenuBean dashboardMenuBean;
    @Autowired
	public SimpleLoginView loginView;
	@Autowired
	public TaskPage taskPageView;
	@Autowired
	public Register registerView;
    @Autowired
    public ProjectManagementPage ProjectManagementView;

    public LoginController loginController;


    @PostConstruct
    void setup() {
        dashboardMenuBean.getDashboardMenu().setupUI(this);
        loginController = new LoginController(this,dashboardMenuBean);
    }

	@Override
    protected void init(VaadinRequest request) {
        //
        // Create a new instance of the navigator. The navigator will attach
        // itself automatically to this view.
        //
        new Navigator(this, this);

        //
        // The initial log view where the user can login to the application
        //
        getNavigator().addView(SimpleLoginView.NAME, loginView);

        //
        // Add the main view of the application
        //
        getNavigator().addView(TaskPage.NAME, taskPageView);

        //
        getNavigator().addView(Register.NAME, registerView);

        //
        // Project management view displayed when user is not enrolled to any project
        //
        getNavigator().addView(ProjectManagementPage.NAME, ProjectManagementView);


        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        //
        getNavigator().addViewChangeListener(new ViewChangeListener() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 7439433006285214464L;

			@Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof SimpleLoginView;
                boolean isRegisterView = event.getNewView() instanceof Register;
                boolean isTaskView = event.getNewView() instanceof TaskPage;
                boolean isProjectManagementView = event.getNewView() instanceof ProjectManagementPage;

                if (!isLoggedIn && !isLoginView && !isRegisterView && (isTaskView || isProjectManagementView)) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo(SimpleLoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;

                } else if (!isRegisterView && !isLoginView && !isTaskView && !isProjectManagementView) {
                    getNavigator().navigateTo(Register.NAME);
                    return false;

                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {

            }
        });
    }
}
