package org.freelo.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.navigator.VaadinView;

import javax.annotation.PostConstruct;

/**
 * Created by karol on 17.11.14.
 */
@VaadinView(name="LoginView")
@Scope("prototype")
public class LoginView extends VerticalLayout implements View {
    private TextField getLogin = new TextField("login name");
    private final PasswordField getPassword = new PasswordField("password");
    VerticalLayout vLayout;
    Panel mainLogin;
    VerticalLayout vLayout2;
    public Button startRegistration;
    public Button LoginButton;

    @PostConstruct
    public void PostConstruct() {

        startRegistration = new Button("Registration");
        startRegistration.addStyleName(Reindeer.BUTTON_LINK);

        LoginButton = new Button("Login");
        //Layout of main page
        vLayout = new VerticalLayout();

        vLayout.setSizeFull();
        //Item allocation
        //Main Login Panel
        mainLogin = new Panel("Login");
        mainLogin.setHeight("250px");
        mainLogin.setWidth("350px");
        //Another vertical layout to allocate items
        vLayout2 = new VerticalLayout();
        vLayout2.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        vLayout2.addComponent(getLogin);
        vLayout2.addComponent(getPassword);
        vLayout2.addComponent(LoginButton);
        vLayout2.addComponent(startRegistration);
        mainLogin.setContent(vLayout2);
        vLayout.addComponent(mainLogin);
        vLayout.setComponentAlignment(mainLogin, Alignment.MIDDLE_CENTER);
        addComponent(vLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Welcome !");
    }
}