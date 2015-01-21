package org.freelo.view.ProjectManagement;

import com.vaadin.data.Item;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.model.users.User;

/**
 * Created by Konrad on 2015-01-13.
 */
public class ManageProjectWindow extends Window {
    private static final long serialVersionUID = 5683290459141040269L;
    //        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //public Button createButton;
    //public ProjectItem pi;
    //CreateProjectSubwindowController c;

    User user;
    //private final BeanFieldGroup<User> fieldGroup;





    public ManageProjectWindow(final User user, String name) {
        super("Project: " + name);
        //c = new CreateProjectSubwindowController(this);

        this.user = user;

        center();
        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight("500px");
        setWidth("640px");
        //setHeight(50.0f, Unit.PERCENTAGE);
        //setWidth(40.0f, Unit.PERCENTAGE);
        //setPositionY(50);
        //setPositionX(50);

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

        detailsWrapper.addComponent(buildDetailsTab(name));
        detailsWrapper.addComponent(buildProjectMembersTab());


        content.addComponent(buildFooter());

        //if (preferencesTabOpen) {
        //    detailsWrapper.setSelectedTab(1);
        //}
        //fieldGroup = new BeanFieldGroup<User>(User.class);
        //fieldGroup.bindMemberFields(this);
        //fieldGroup.setItemDataSource(user);
    }

    private Component buildDetailsTab(String name) {
        VerticalLayout root = new VerticalLayout();
        root.setCaption("Details");
        root.setIcon(FontAwesome.EDIT);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();

        Label projectDetailsLabel = new Label("Project Details");
        projectDetailsLabel.addStyleName(ValoTheme.LABEL_H4);
        projectDetailsLabel.addStyleName(ValoTheme.LABEL_COLORED);


        TextField projectNameField = new TextField("Project Name");
        projectNameField.setValue(name);


        root.addComponent(projectDetailsLabel);
        root.addComponent(projectNameField);
        root.setExpandRatio(projectNameField, 1);

        return root;
    }

    private Component buildProjectMembersTab() {
        VerticalLayout root = new VerticalLayout();
        root.setCaption("Project members");
        root.setIcon(FontAwesome.USER);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();

        final String member = user.getFirstName()+ " " +user.getLastName();

        Label projectMembersLabel = new Label("Project Members");
        projectMembersLabel.addStyleName(ValoTheme.LABEL_H4);
        projectMembersLabel.addStyleName(ValoTheme.LABEL_COLORED);



        //////////Members table
        final Table membersTable = new Table();
        membersTable.addStyleName("multirowheaders");
        membersTable.addContainerProperty("Name", String.class, null);
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



        final CheckBox deletingProjectCheckbox = new CheckBox();
        final CheckBox managingSprintsCheckbox = new CheckBox();
        final CheckBox addingMembersCheckbox = new CheckBox();
        final CheckBox deletingMembersCheckbox = new CheckBox();
        final CheckBox addingTasksCheckbox = new CheckBox();
        final CheckBox deletingTasksCheckbox = new CheckBox();


        //Adding user to table
        deletingProjectCheckbox.setValue(true);
        managingSprintsCheckbox.setValue(true);
        addingMembersCheckbox.setValue(true);
        deletingMembersCheckbox.setValue(true);
        addingTasksCheckbox.setValue(true);
        deletingTasksCheckbox.setValue(true);
        membersTable.addItem(new Object[] {member, deletingProjectCheckbox, managingSprintsCheckbox, addingMembersCheckbox,
                        deletingMembersCheckbox, addingTasksCheckbox, deletingTasksCheckbox},
                0);
        //Object newItemId = membersTable.addItem();
        //Item row1 = membersTable.getItem(newItemId);
        //CheckBox addingMembersCheckbox = new CheckBox();
        //row1.getItemProperty("Name").setValue(member);
        //row1.getItemProperty("Adding members").setValue(true);
        //}
        membersTable.setPageLength(membersTable.size());
        membersTable.setSelectable(true);
        //membersTable.setEditable(true);

        Button deleteMemberButton = new Button("Delete member", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                membersTable.removeItem(0);
            }
        });

        //////////Adding user field
        HorizontalLayout addMemberContainer = new HorizontalLayout();
        //addMemberContainer.setCaption("Add member");
        final ComboBox addMemberBox = new ComboBox();
        addMemberBox.addItem(member);
        Button addMemberButton = new Button("Add member", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                membersTable.addItem(new Object[] {addMemberBox.getValue(), deletingProjectCheckbox, managingSprintsCheckbox, addingMembersCheckbox,
                                deletingMembersCheckbox, addingTasksCheckbox, deletingTasksCheckbox},
                        0);
            }
        });

        addMemberContainer.addComponent(addMemberBox);
        addMemberContainer.addComponent(addMemberButton);


        root.addComponent(projectMembersLabel);
        root.addComponent(membersTable);
        root.addComponent(deleteMemberButton);
        root.addComponent(addMemberContainer);
        root.setExpandRatio(addMemberContainer, 1);
        root.setComponentAlignment(projectMembersLabel, Alignment.TOP_LEFT);
        root.setComponentAlignment(membersTable, Alignment.TOP_LEFT);
        root.setComponentAlignment(addMemberContainer, Alignment.TOP_LEFT);
        return root;
    }


    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button deleteButton = new Button("Delete Project");
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);


        Button updateButton = new Button("Update", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                //UI.getCurrent().getNavigator().navigateTo(TaskPage.NAME);
                close();
            }
        });

        footer.addComponent(updateButton);
        footer.addComponent(deleteButton);
        footer.setComponentAlignment(deleteButton, Alignment.TOP_LEFT);
        footer.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        return footer;
    }

}
