package org.freelo.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.navigator.SpringViewProvider;

/**
 * Created by karol on 17.11.14.
 */
@VaadinUI
public class MyVaadinUI extends UI {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SpringViewProvider viewProvider;

    Navigator navigator;
    protected static final String LOGINVIEW = "LoginView";
    @Autowired
    LoginView loginView;

    @Override
    public void init(VaadinRequest request) {
        Page.getCurrent().setTitle("Example IT App");
        navigator = new Navigator(this,this);
        navigator.addProvider(viewProvider);
        navigator.addView("",viewProvider.getView(LOGINVIEW));
    }

}