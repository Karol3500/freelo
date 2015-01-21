package org.freelo.view.Dashboard;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.controller.dashboard.addFriendController;
import org.freelo.model.users.Friends;
import org.freelo.model.users.User;
import org.freelo.view.Dashboard.Subwindows.MessageWindow;
import org.freelo.view.Dashboard.Subwindows.ProfilePreferencesWindow;
import org.freelo.view.Dashboard.Subwindows.addFriendWindow;
import org.freelo.view.SimpleLoginUI;

import java.io.File;
import java.util.List;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */

public final class DashboardMenu extends CustomComponent implements View {
	private static final long serialVersionUID = 1L;
	public static final String ID = "dashboard-menu";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    private MenuItem settingsItem;
    final MenuBar settings = new MenuBar();
    public SimpleLoginUI ui;
    final CssLayout menuContent = new CssLayout();
    CssLayout friendListLayout = new CssLayout();
    public List<Friends> friends;
    public addFriendController friendController;

    public DashboardMenu() {
        addStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();
        buildContent();
        setCompositionRoot(menuContent);
        menuContent.addComponent(buildTitle());
        friendController = new addFriendController(this);
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
        Panel friendPanel = buildFriendPanel();
        friendPanel.setSizeFull();
        //friendPanel.setHeight("100px");
        menuContent.addComponent(friendPanel);

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
        settings.addStyleName("user-menu");
        final User user = getCurrentUser();
        String username = user.getEmail();
        settings.addStyleName("user-menu");

        settingsItem = settings.addItem("", new FileResource(new File(user.getPicturePath())), null);
        settingsItem.setText(username);
        settingsItem.addItem("Edit Profile", new Command() {
			private static final long serialVersionUID = 1L;
			@Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow subWind = new ProfilePreferencesWindow(user, false);
                // Add it to the root component
                UI.getCurrent().addWindow(subWind);
                subWind.addCloseListener(new Window.CloseListener() {
                    public void windowClose(Window.CloseEvent e) {
                        updateUserMenu();
                    }
                });
            }
        });

        settingsItem.addItem("Preferences", new Command() {
			private static final long serialVersionUID = 1L;

			@Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(user, true);
            }
        });
        settingsItem.addSeparator();
            settingsItem.addItem("Sign Out", new Command() {
				private static final long serialVersionUID = 1L;

			@Override
            public void menuSelected(final MenuItem selectedItem) {
                getSession().setAttribute("user", null);
                ui.getSession().close();
                getUI().getPage().setLocation( "/" );
            }
        });
        return settings;
    }

    private void updateUserMenu() {
        settings.removeItems();
        buildUserMenu();
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
			private static final long serialVersionUID = 1L;

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
        friendListLayout = new CssLayout();
        friendListLayout.addStyleName("valo-frienditems");
        friendListLayout.setHeight(100.0f, Unit.PERCENTAGE);

        friendListLayout.addComponent(addFriendButton());
        return friendListLayout;

    }

    private Button addFriendButton(){
        Button addFriendButton = new Button("+ Add Friend", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                final addFriendWindow subWind = new addFriendWindow();
                friendController.addFriend(subWind);
                // Add it to the root component
                UI.getCurrent().addWindow(subWind);
                subWind.addCloseListener(new Window.CloseListener() {
                    public void windowClose(Window.CloseEvent e) {
                        //profilePic.setSource(new FileResource(new File(user.getPicturePath())));
                        String friendName = subWind.getUserName();
                        if (friendName != null) buildFriend(friendName, "..");
                    }
                });
            }
        });
        addFriendButton.setStyleName(ValoTheme.BUTTON_SMALL);
        addFriendButton.setWidth("100%");
        addFriendButton.setImmediate(true);

        return addFriendButton;
    }

    public void buildFriend(String firstName, String lastName){
        Component friendListComponent = new friendViewButton(firstName, lastName);

        friendListLayout.addComponent(friendListComponent);
    }

    public void removeFriends(){
        friendListLayout.removeAllComponents();
        friendListLayout.addComponent(addFriendButton());

    }


    Panel buildFriendPanel(){
        Panel friendPanel = new Panel("List of friends");
        Component friendList = buildFriendList();
        friendList.setSizeFull();

        VerticalLayout friendLayout = new VerticalLayout();

        friendPanel.setContent(friendLayout);
        friendPanel.setStyleName("FriendPanel");
        friendPanel.setWidth("95%");
        friendPanel.setHeight("100%");
        friendLayout.setSizeFull();
        friendLayout.addComponent(friendList);

        //friendLayout.setComponentAlignment(friendList, Alignment.TOP_CENTER);
        return friendPanel;
    }

    @Override
    public void attach() {
        super.attach();
    }

    public final class ValoMenuItemButton extends Button {
		private static final long serialVersionUID = 1L;

        private final DashboardViewType view;

        public ValoMenuItemButton(final DashboardViewType view) {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getViewName().substring(0, 1).toUpperCase()
                    + view.getViewName().substring(1));
            //DashboardEventBus.register(this);
            addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
                public void buttonClick(final ClickEvent event) {
                    UI.getCurrent().getNavigator().navigateTo(view.getViewName());
                    //UI.getCurrent().getNavigator().navigateTo(ProjectManagementPage.NAME);
                }
            });

        }
    }

    public final class friendViewButton extends Button {
		private static final long serialVersionUID = 1L;

        public friendViewButton(String firstName, String lastName) {
            setPrimaryStyleName("valo-menu-item");
            setIcon(FontAwesome.USER);
            setCaption(firstName.substring(0, 1).toUpperCase()+ firstName.substring(1)+" "+
                    lastName.substring(0, 1).toUpperCase()+ lastName.substring(1));

            addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

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
