package org.freelo.view;
import com.vaadin.navigator.*;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;



public class Login {
    Navigator navigator;
    protected static final String LOGINVIEW = "LoginView";

    public class LoginView extends VerticalLayout implements View {

        private TextField getLogin = new TextField("login name");
        private final PasswordField getPassword = new PasswordField("password");

        public LoginView() {

            Button startRegistration = new Button("com.vaadin.ui.Registration",
                    new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            navigator.navigateTo(LOGINVIEW);
                        }
                    });
            startRegistration.addStyleName(Reindeer.BUTTON_LINK);

            Button LoginButton = new Button("Login",
                    new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            navigator.navigateTo("");
                        }
                    });


            //Layout of main page
            VerticalLayout vLayout = new VerticalLayout();

            vLayout.setSizeFull();
            //Item allocation
            //Main Login Panel
            Panel mainLogin = new Panel("Login");
            mainLogin.setHeight("250px");
            mainLogin.setWidth("350px");
            //Another vertical layout to allocate items
            VerticalLayout vLayout2 = new VerticalLayout();
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

    }


