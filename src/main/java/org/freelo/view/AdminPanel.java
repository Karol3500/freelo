package org.freelo.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.Dashboard.DashboardViewType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by Jan on 2014-11-08.
 */

@Component
@Scope("prototype")
public class AdminPanel extends VerticalLayout implements View {

    private static final long serialVersionUID = 645144581199267137L;
    public static String NAME = "AdminPanel";



    public AdminPanel(){

        setSpacing(true);
        setMargin(true);
        setSizeFull();

        com.vaadin.ui.Component adminPanel = buildAdminPanel();
        addComponent(adminPanel);
        setComponentAlignment(adminPanel, Alignment.MIDDLE_CENTER);


    }

    private com.vaadin.ui.Component buildAdminPanel() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setHeight("600");
        layout.setWidth("875px");
        layout.setMargin(new MarginInfo(true, true, true, true));
        layout.setStyleName(ValoTheme.LAYOUT_CARD);

        Label adminPanelLabel = new Label("ADMIN PANEL");
        adminPanelLabel.setWidthUndefined();
        adminPanelLabel.addStyleName(ValoTheme.LABEL_H2);
        adminPanelLabel.addStyleName(ValoTheme.LABEL_COLORED);
        adminPanelLabel.addStyleName(ValoTheme.LABEL_BOLD);

        layout.addComponent(adminPanelLabel);

        com.vaadin.ui.Component projectMembers = buildProjectMembersTab();

        layout.addComponent(projectMembers);
        layout.setExpandRatio(projectMembers, 1);
        layout.setComponentAlignment(adminPanelLabel, Alignment.TOP_CENTER);
        //layout.setComponentAlignment(menuItem, Alignment.MIDDLE_CENTER);




        return layout;
    }

    private com.vaadin.ui.Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        menuItemsLayout.setHeight(100.0f, Unit.PERCENTAGE);

        for (final DashboardViewType view : DashboardViewType.values()) {
            com.vaadin.ui.Component menuItemComponent = new ValoMenuItemButton(view);
            menuItemsLayout.addComponent(menuItemComponent);
        }
        return menuItemsLayout;

    }

    private com.vaadin.ui.Component buildProjectMembersTab() {
        final VerticalLayout root = new VerticalLayout();
        root.setSpacing(true);
        root.setSizeFull();

        Label projectMembersLabel = new Label("Users");
        projectMembersLabel.addStyleName(ValoTheme.LABEL_H4);


        //////////Members table
        final Table membersTable = new Table();
        membersTable.setWidth("800px");
        membersTable.addStyleName("multirowheaders");
        membersTable.addContainerProperty("Name", String.class, null);
        membersTable.addContainerProperty("Admin", CheckBox.class,  null);
        membersTable.addContainerProperty("Deleting project", CheckBox.class,  null);
        membersTable.addContainerProperty("Managing sprints", CheckBox.class,  null);
        membersTable.addContainerProperty("Adding members", CheckBox.class,  null);
        membersTable.addContainerProperty("Deleting members", CheckBox.class,  null);
        membersTable.addContainerProperty("Adding tasks", CheckBox.class,  null);
        membersTable.addContainerProperty("Deleting tasks", CheckBox.class,  null);
        membersTable.setColumnHeader("Deleting project", "<div>Deleting</div><br/>project");
        membersTable.setColumnHeader("Managing sprints", "<div>Managing</div><br/>sprints");
        membersTable.setColumnHeader("Adding members", "<div>Adding</div><br/>members");
        membersTable.setColumnHeader("Deleting members", "<div>Deleting</div><br/>members");
        membersTable.setColumnHeader("Adding tasks", "<div>Adding</div><br/>tasks");
        membersTable.setColumnHeader("Deleting tasks", "<div>Deleting</div><br/>tasks");
        /**
         membersTable.setColumnAlignment("Deleting project",
         Table.ALIGN_CENTER);
         membersTable.setColumnAlignment("Managing sprints",
         Table.ALIGN_CENTER);
         membersTable.setColumnAlignment("Adding members",
         Table.ALIGN_CENTER);
         membersTable.setColumnAlignment("Deleting members",
         Table.ALIGN_CENTER);
         membersTable.setColumnAlignment("Adding tasks",
         Table.ALIGN_CENTER);
         membersTable.setColumnAlignment("Deleting tasks",
         Table.ALIGN_CENTER);
         **/


        membersTable.setPageLength(8);
        membersTable.setSelectable(true);
        membersTable.setImmediate(true);

        List<User> users = UserManagement.getUsers();
        for (User element: users){
            getAppMembers(element.getEmail(), membersTable);
        }

        Button deleteMemberButton = new Button("Delete user", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                //todo: controller - method removing project members from database
                //System.out.println(membersTable.getValue().toString());
                membersTable.removeItem(membersTable.getValue());
            }
        });
        Button banMemberButton = new Button("Ban user", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                //todo: controller - method removing project members from database
                membersTable.removeItem(membersTable.getValue());
            }
        });
        Button updateButton = new Button("Update", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                //UI.getCurrent().getNavigator().navigateTo(TaskPage.NAME);

            }
        });



        deleteMemberButton.setStyleName(ValoTheme.BUTTON_DANGER);
        banMemberButton.setStyleName(ValoTheme.BUTTON_DANGER);

        com.vaadin.ui.Component menuItem = buildMenuItems();
        HorizontalLayout buttonContainer = new HorizontalLayout();
        buttonContainer.setWidth("100%");
        buttonContainer.addComponent(updateButton);
        buttonContainer.addComponent(deleteMemberButton);
        buttonContainer.addComponent(banMemberButton);
        buttonContainer.addComponent(menuItem);
        buttonContainer.setExpandRatio(menuItem, 1);
        buttonContainer.setSpacing(true);
        buttonContainer.setComponentAlignment(deleteMemberButton, Alignment.TOP_LEFT);
        buttonContainer.setComponentAlignment(banMemberButton, Alignment.TOP_LEFT);
        buttonContainer.setComponentAlignment(menuItem, Alignment.TOP_RIGHT);



        root.addComponent(projectMembersLabel);
        root.addComponent(membersTable);
        root.addComponent(buttonContainer);
        root.setExpandRatio(buttonContainer, 1);
        root.setComponentAlignment(projectMembersLabel, Alignment.TOP_LEFT);
        root.setComponentAlignment(membersTable, Alignment.TOP_LEFT);
        return root;
    }
    private void getAppMembers(String members, final Table membersTable){

        //todo: controller -  method retrieving permissions from database

        // aaaa chuuuj niechce mi sie

        final CheckBox AdminCheckbox = new CheckBox();
        final CheckBox deletingProjectCheckbox = new CheckBox();
        final CheckBox managingSprintsCheckbox = new CheckBox();
        final CheckBox addingMembersCheckbox = new CheckBox();
        final CheckBox deletingMembersCheckbox = new CheckBox();
        final CheckBox addingTasksCheckbox = new CheckBox();
        final CheckBox deletingTasksCheckbox = new CheckBox();

        AdminCheckbox.setValue(false);
        deletingProjectCheckbox.setValue(false);
        managingSprintsCheckbox.setValue(false);
        addingMembersCheckbox.setValue(false);
        deletingMembersCheckbox.setValue(false);
        addingTasksCheckbox.setValue(false);
        deletingTasksCheckbox.setValue(false);

        if (members.equals("arturwaz@freelo.com")){
            AdminCheckbox.setValue(true);
            deletingProjectCheckbox.setValue(true);
            managingSprintsCheckbox.setValue(true);
            addingMembersCheckbox.setValue(true);
            deletingMembersCheckbox.setValue(true);
            addingTasksCheckbox.setValue(true);
            deletingTasksCheckbox.setValue(true);
        }

        membersTable.addItem(new Object[]{members, AdminCheckbox, deletingProjectCheckbox, managingSprintsCheckbox, addingMembersCheckbox,
                        deletingMembersCheckbox, addingTasksCheckbox, deletingTasksCheckbox},
                null);
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
       ;
    }




}


