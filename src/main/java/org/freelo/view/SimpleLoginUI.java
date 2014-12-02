package org.freelo.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Created by Konrad on 2014-11-17.
 */
@Theme("themefreelo")
public class SimpleLoginUI extends UI {

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
        getNavigator().addView(SimpleLoginView.NAME, SimpleLoginView.class);//

        //
        // Add the main view of the application
        //
        getNavigator().addView(TaskPage.NAME, TaskPage.class);

        //
        getNavigator().addView(Register.NAME, Register.class);
        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        //
        getNavigator().addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof SimpleLoginView;
                boolean isRegisterView = event.getNewView() instanceof Register;
                boolean isTaskView = event.getNewView() instanceof TaskPage;

                if (!isLoggedIn && !isLoginView && !isRegisterView && isTaskView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo(SimpleLoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;

                } else if (!isRegisterView && !isLoginView && !isTaskView) {
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
