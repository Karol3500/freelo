package org.freelo.view.ProjectManagement;

import com.vaadin.data.Item;

import com.vaadin.data.Property;
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


    User user;

    public ManageProjectWindow(final User user, String name) {
        super("Project: " + name);
        this.user = user;

        center();
        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
        setHeight("700px");
        setWidth("743px");

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(false, false, false, false));
        setContent(content);

        Component projectMembers = buildProjectMembersTab(name);
        Component footer = buildFooter();
        content.addComponent(projectMembers);
        content.addComponent(footer);
        content.setExpandRatio(projectMembers, 1);

    }


    private Component buildProjectMembersTab(String name) {
        final VerticalLayout root = new VerticalLayout();
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();

        Label projectDetailsLabel = new Label("Project Name");
        projectDetailsLabel.addStyleName(ValoTheme.LABEL_H4);
        projectDetailsLabel.addStyleName(ValoTheme.LABEL_COLORED);


        TextField projectNameField = new TextField();
        projectNameField.setValue(name);




        Label projectMembersLabel = new Label("Project Members");
        projectMembersLabel.addStyleName(ValoTheme.LABEL_H4);
        projectMembersLabel.addStyleName(ValoTheme.LABEL_COLORED);



        //////////Members table
        final Table membersTable = new Table();
        membersTable.setWidth("670px");
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


        membersTable.setPageLength(6);
        membersTable.setSelectable(true);
        membersTable.setImmediate(true);

        //todo: controller - method retrieving application members from database
        //todo: controller - method retrieving project members from database
        String[] members = {"Jan Dziergwa", "Konrad Jażownik", "Karol Posiła", "Adrian Cyga", "Artur Wąż", "Piotr Bienias", "Rubens Diaz"};
        int size =  members.length;

        for (int i=0; i<size; i++){
            getProjectMembers(members[i], membersTable);
        }

                Button deleteMemberButton = new Button("Delete member", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                //todo: controller - method removing project members from database
                membersTable.removeItem(membersTable.getValue());
            }
        });


        //////////Adding user field
        HorizontalLayout addMemberContainer = new HorizontalLayout();
        final ComboBox addMemberBox = new ComboBox();

        for (int i=0; i<size; i++){
            addMemberBox.addItem(members[i]);
        }

        Button addMemberButton = new Button("Add member", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                addProjectMember(membersTable, addMemberBox);
            }
        });

        addMemberContainer.addComponent(addMemberBox);
        addMemberContainer.addComponent(addMemberButton);



        root.addComponent(projectDetailsLabel);
        root.addComponent(projectNameField);
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


    private void addProjectMember(final Table membersTable, ComboBox addMemberBox) {
        //Adding user to table



        final CheckBox deletingProjectCheckbox = new CheckBox();
        final CheckBox managingSprintsCheckbox = new CheckBox();
        final CheckBox addingMembersCheckbox = new CheckBox();
        final CheckBox deletingMembersCheckbox = new CheckBox();
        final CheckBox addingTasksCheckbox = new CheckBox();
        final CheckBox deletingTasksCheckbox = new CheckBox();

        deletingProjectCheckbox.setValue(false);
        managingSprintsCheckbox.setValue(false);
        addingMembersCheckbox.setValue(false);
        deletingMembersCheckbox.setValue(false);
        addingTasksCheckbox.setValue(true);
        deletingTasksCheckbox.setValue(false);

        //todo: controller - method adding member to project database
        membersTable.addItem(new Object[]{addMemberBox.getValue().toString(), deletingProjectCheckbox, managingSprintsCheckbox, addingMembersCheckbox,
                        deletingMembersCheckbox, addingTasksCheckbox, deletingTasksCheckbox},
                null);

    }
    private void getProjectMembers(String members, final Table membersTable){

        //todo: controller -  method retrieving permissions from database
        final CheckBox deletingProjectCheckbox = new CheckBox();
        final CheckBox managingSprintsCheckbox = new CheckBox();
        final CheckBox addingMembersCheckbox = new CheckBox();
        final CheckBox deletingMembersCheckbox = new CheckBox();
        final CheckBox addingTasksCheckbox = new CheckBox();
        final CheckBox deletingTasksCheckbox = new CheckBox();

        deletingProjectCheckbox.setValue(true);
        managingSprintsCheckbox.setValue(true);
        addingMembersCheckbox.setValue(true);
        deletingMembersCheckbox.setValue(true);
        addingTasksCheckbox.setValue(true);
        deletingTasksCheckbox.setValue(true);

        membersTable.addItem(new Object[]{members, deletingProjectCheckbox, managingSprintsCheckbox, addingMembersCheckbox,
                        deletingMembersCheckbox, addingTasksCheckbox, deletingTasksCheckbox},
                null);
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
