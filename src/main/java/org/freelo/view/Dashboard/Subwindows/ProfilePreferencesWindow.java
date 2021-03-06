package org.freelo.view.Dashboard.Subwindows;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.*;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;

import java.io.File;

@SuppressWarnings("serial")
public class ProfilePreferencesWindow extends Window {

    public static final String ID = "profilepreferenceswindow";

    private final BeanFieldGroup<User> fieldGroup;
    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */
    @PropertyId("firstName")
    private TextField firstNameField;
    @PropertyId("lastName")
    private TextField lastNameField;
    @PropertyId("email")
    private TextField emailField;
    @PropertyId("password")
    private PasswordField passwordField;

    Button changePasswordButton;

    UserManagement um = new UserManagement();
    User user;

    Image profilePic;

    public ProfilePreferencesWindow(final User user) {
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(50.0f, Unit.PERCENTAGE);
        setWidth(40.0f, Unit.PERCENTAGE);

        this.user = user;
        //userTemp = user;

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildProfileTab());

        fieldGroup = new BeanFieldGroup<User>(User.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(user);

        content.addComponent(buildFooter());

    }

    private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Profile");
        root.setIcon(FontAwesome.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        VerticalLayout pic = new VerticalLayout();
        pic.setSizeUndefined();
        pic.setSpacing(true);
        //final Image profilePic = new Image(null, new ThemeResource("img/profile-pic-300px.jpg"));
        final Image profilePic = new Image(null, new FileResource(new File(user.getPicturePath())));
        profilePic.setWidth(100.0f, Unit.PIXELS);
        pic.addComponent(profilePic);

        Button upload = new Button("Change…", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                PictureSubwindow subPic = new PictureSubwindow();
                // Add it to the root component
                UI.getCurrent().addWindow(subPic);
                subPic.addCloseListener(new CloseListener() {
                    public void windowClose(CloseEvent e) {
                        profilePic.setSource(new FileResource(new File(user.getPicturePath())));
                    }
                });
            }
        });
        upload.addStyleName(ValoTheme.BUTTON_TINY);
        pic.addComponent(upload);

        root.addComponent(pic);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        Label section = new Label("User Details");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        firstNameField = new TextField("First Name");
        details.addComponent(firstNameField);
        firstNameField.setWidth("100%");

        lastNameField = new TextField("Last Name");
        details.addComponent(lastNameField);

        emailField = new TextField("Email");
        emailField.setWidth("100%");
        emailField.setNullRepresentation("");
        details.addComponent(emailField);

        passwordField = new PasswordField("Password");
        passwordField.setValue(user.getPassword());
        passwordField.setWidth("100%");
        passwordField.setReadOnly(true);

        changePasswordButton = new Button("Change Password..");

        changePasswordButton.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                PasswordSubwindow sub = new PasswordSubwindow(user);
                // Add it to the root component
                UI.getCurrent().addWindow(sub);
            }
        });

        details.addComponents(emailField, passwordField, changePasswordButton);

        return root;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("OK");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {

                try {
                    if (fieldGroup.isModified()) {
                        fieldGroup.commit();
                        um.userUpdate(user);
                        getSession().setAttribute("userClass", user);
                        Notification success = new Notification(
                                "Profile updated successfully");
                        success.setDelayMsec(2000);
                        success.setStyleName("bar success small");
                        success.setPosition(Position.BOTTOM_CENTER);
                        success.show(Page.getCurrent());
                    }
                    close();
                } catch (Exception e) {
                    Notification.show("Error while updating profile",
                            Type.ERROR_MESSAGE);
                }

            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    public static void open(final User user, final boolean preferencesTabActive) {
        Window w = new ProfilePreferencesWindow(user);
        UI.getCurrent().addWindow(w);
        w.focus();
    }
}
