package org.freelo.view.Dashboard;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.model.users.User;
import org.freelo.view.ProjectManagement.Subwindow;
import org.freelo.view.SimpleLoginUI;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */

public final class DashboardMenu extends CustomComponent implements View {

    public static final String ID = "dashboard-menu";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    private MenuItem settingsItem;
    public SimpleLoginUI ui;
    final CssLayout menuContent = new CssLayout();
    public DashboardMenu() {
        addStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();
        buildContent();
        setCompositionRoot(menuContent);
        menuContent.addComponent(buildTitle());
    }

    public void setupUI(SimpleLoginUI ui){
        this.ui = ui;
        setupAfterLogin();
    }

    public void setupAfterLogin(){
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());
        menuContent.addComponent(new Label(""));
        Panel userPanel = buildFriendPanel();
        userPanel.setHeight("100%");
        menuContent.addComponent(userPanel);


    }

    private void buildContent() {
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");
    }

    private Component buildTitle() {
        Label logo = new Label("Think - Plan - Do <strong>Freelo</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }

    private User getCurrentUser() {
        return (User) VaadinSession.getCurrent().getAttribute("userClass");
    }

    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        final User user = getCurrentUser();
        settingsItem = settings.addItem("", new ThemeResource(
                "img/profile-pic-300px.jpg"), null);
        String username = String.valueOf(ui.getSession().getAttribute("user"));
        settingsItem.setText(username);
        settingsItem.addItem("Edit Profile", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(user, false);
            }
        });
        settingsItem.addItem("Preferences", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(user, true);
            }
        });
        settingsItem.addSeparator();
            settingsItem.addItem("Sign Out", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                getSession().setAttribute("user", null);
                ui.getSession().close();
                getUI().getPage().setLocation( "/" );
            }
        });
        return settings;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        menuItemsLayout.setHeight(100.0f, Unit.PERCENTAGE);

        for (final DashboardViewType view : DashboardViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);
            menuItemsLayout.addComponent(menuItemComponent);
        }
        return menuItemsLayout;

    }

    private Component buildFriendList() {
        CssLayout friendListLayout = new CssLayout();
        friendListLayout.addStyleName("valo-frienditems");
        friendListLayout.setHeight(100.0f, Unit.PERCENTAGE);
        /*
        for (final DashboardViewType view : DashboardViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);

            menuItemsLayout.addComponent(menuItemComponent);
        }*/

        Button addFriendButton = new Button("+ add Friend");
        addFriendButton.setStyleName(ValoTheme.BUTTON_SMALL);
        addFriendButton.setWidth("80%");
        addFriendButton.setImmediate(true);
        friendListLayout.addComponent(addFriendButton);
        //friendListLayout.addComponent(new Label(""));
        for (int i=0; i<3; ++i) {
            Component friendListComponent = new friendViewButton(getCurrentUser());
            friendListLayout.addComponent(friendListComponent);

        }

        friendListLayout.addComponent(new Label(""));
        return friendListLayout;

    }

    Panel buildFriendPanel(){
        Panel friendPanel = new Panel("List of friends");
        Component friendList = buildFriendList();

        VerticalLayout friendLayout = new VerticalLayout();

        friendPanel.setContent(friendLayout);
        friendPanel.setStyleName("FriendPanel");
        friendPanel.setWidth("95%");
        friendPanel.setHeight("100%");
        friendLayout.setSizeFull();

        friendLayout.addComponent(friendList);
        //friendLayout.setComponentAlignment(friendPanel, Alignment.TOP_CENTER);


        return friendPanel;
    }

    @Override
    public void attach() {
        super.attach();
    }

    public final class ValoMenuItemButton extends Button {

        private final DashboardViewType view;

        public ValoMenuItemButton(final DashboardViewType view) {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getViewName().substring(0, 1).toUpperCase()
                    + view.getViewName().substring(1));
            //DashboardEventBus.register(this);
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                    UI.getCurrent().getNavigator().navigateTo(view.getViewName());
                    //UI.getCurrent().getNavigator().navigateTo(ProjectManagementPage.NAME);
                }
            });

        }
    }

    public final class friendViewButton extends Button {

        public friendViewButton(User user) {
            setPrimaryStyleName("valo-menu-item");
            setIcon(FontAwesome.USER);
            setCaption(user.getFirstName().substring(0, 1).toUpperCase()+ user.getFirstName().substring(1)+" "+
                    user.getLastName().substring(0, 1).toUpperCase()+ user.getLastName().substring(1));

            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                    MessageWindow.open(getCurrentUser());
                }
            });

        }
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {
        //String username = String.valueOf(getSession().getAttribute("user"));
        //this.name = username;
    }
}
