package org.freelo.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;


/**
 * Created by Konrad on 2014-11-16.
 */
public class Register extends VerticalLayout implements View {

    public static final String NAME = "Registration";
    protected final PasswordField password = new PasswordField("Password");
    protected final Label title = new Label("Registration");
    protected final TextField username = new TextField("Username");

    protected final TextField mail = new TextField("E-mail Address");
    protected final TextField name = new TextField("Name");
    protected final TextField surname = new TextField("Surname");

    public Register(){
        password.setInputPrompt("Please use big letters and numbers !");
        password.isRequired();
        username.isRequired();
        mail.isRequired();
        Button RegisterMe;
        Button BackButton;

        setSizeFull();
        BackButton = new Button("Back", new Button.ClickListener() {
            @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getNavigator().navigateTo(SimpleLoginView.NAME);

            }
        });
        RegisterMe = new Button("Proceed", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String username = mail.getValue();
                //Add some controller handler to obtain data from method
                RegisterNewUser();
                //
                Notification welcome = new Notification(username + " registered !");
                welcome.setDelayMsec(20000);
                welcome.setPosition(Position.MIDDLE_CENTER);
                boolean password_status = check_the_password();
                getUI().getNavigator().navigateTo(SimpleLoginView.NAME);
                welcome.show(Page.getCurrent());

            }

        });


        VerticalLayout mainregpage = new VerticalLayout(title, username, password, mail, name, surname, RegisterMe, BackButton);
        mainregpage.setMargin(new MarginInfo(true, true, true, true));
        mainregpage.setSpacing(true);
        mainregpage.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        VerticalLayout viewLayout = new VerticalLayout();
        viewLayout.setSizeFull();
        viewLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        viewLayout.addComponent(mainregpage);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(viewLayout);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        username.focus();
    }

    protected String[] RegisterNewUser() {
        String[] user_data = {username.getValue(), password.getValue(), mail.getValue(), name.getValue(), surname.getValue()};
        return user_data;
    }

    private boolean check_the_password() {

        String pass = password.getValue();
        boolean digit_stat;
        boolean Upstatus = false;
        boolean len_stat;
        int pass_length = pass.length();

        if(pass_length >=8){
            len_stat = true;
        } else {
            len_stat = false;
        }

        for(int i = 0; i < pass_length; i++){
            if(Character.isUpperCase(i)){
                Upstatus = true;
            } else {
                Upstatus = false;
            }
        }
        if(pass.matches(".*\\d.*")){
            digit_stat = true;
        } else {
            digit_stat = false;
        }
        if(len_stat && Upstatus && digit_stat){
        return true;
        } else {
            return false;
        }
    }}
