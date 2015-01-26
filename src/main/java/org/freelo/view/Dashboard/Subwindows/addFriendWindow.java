package org.freelo.view.Dashboard.Subwindows;

import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 20-01-2015.
 */
public class addFriendWindow extends Window {
    private static final long serialVersionUID = 5678234591401040269L;

    private String userName = null;
    public TextField friendField;
    public Button addFriendButton;
    public Select friendSelect;
    public addFriendWindow() {
        super("Add User");
        center();
        setClosable(true);
        setResizable(false);
        VerticalLayout main = new VerticalLayout();
        main.addStyleName("userpopup");
        main.setSizeFull();
        setContent(main);
        setHeight("150px");
        setWidth("700px");

        main.addComponent(buildFriendField());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Component buildFriendField() {
        HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("user-form");
        friendSelect = new Select();
        populate_friend_list();
        friendField = new TextField();
        friendField.setInputPrompt("eg. joe@email.com");
//        friendField.addValueChangeListener(new Property.ValueChangeListener() {
//            @Override
//            public void valueChange(Property.ValueChangeEvent event) {
//                event.getProperty().getValue()
//            }
//        });
        friendField.setImmediate(true);
        //friendField.addValidator(new UserValidator());
        friendField.setValidationVisible(false);
        friendField.setWidth("100%");
        addFriendButton= new Button("+ Add Friend");
        addFriendButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addFriendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        /*
        addFriendButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                userName = null;
                friendField.setValidationVisible(false);
                try {
                    friendField.validate();
                    userName = friendField.getValue();
                    Notification success = new Notification(userName + " added successfully");
                    success.setDelayMsec(1500);
                    success.show(Page.getCurrent());
                    close();
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                    friendField.setValidationVisible(true);
                }
            }
        });*/

        root.addComponents(friendField,friendSelect, addFriendButton);
        root.setExpandRatio(friendSelect,3.0f);
        root.setExpandRatio(friendField, 2.0f);
        root.setExpandRatio(addFriendButton, 1.0f);

        return root;
    }
    private void populate_friend_list() {
        List<User> friends = UserManagement.getUsers();
        ArrayList<String> elo_friends = convert_to_mail(friends);
        friendSelect.addItems(elo_friends);
        friendSelect.setImmediate(true);
        friendSelect.select(elo_friends.get(0));
        friendSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                String value = event.getProperty().getValue().toString();
                friendField.setValue(value);
            }
        });
    }
    private ArrayList<String> convert_to_mail(List <User> friends) {
        int l = friends.size();
        ArrayList<String> elo_friends = new ArrayList();
        for(int u=0; u<l; u++) {
            elo_friends.add(u,friends.get(u).getEmail());
        }
        System.out.println(elo_friends);
        return elo_friends;
    }

}
