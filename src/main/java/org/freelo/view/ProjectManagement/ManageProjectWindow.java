package org.freelo.view.ProjectManagement;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.projects.Project;
import org.freelo.model.projects.ProjectManagement;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Konrad on 2015-01-13.
 */
public class ManageProjectWindow extends Window {
    private static final long serialVersionUID = 5683290459141040269L;
    Table membersTable;
    String manager;
    Project thisProject;
    String projectName;
    public ManageProjectWindow(String manager, String projectName) {
        super("Project: " + projectName);
        this.manager = manager;
        this.projectName = projectName;
        int managerID = UserManagement.getUserID(manager);
        this.thisProject = ProjectManagement.getProject(managerID, projectName);
        center();
        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
        setHeight("700px");
        setWidth("853px");
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(false, false, false, false));
        setContent(content);
        Component projectMembers = buildProjectMembersTab();
        Component footer = buildFooter(projectName);
        content.addComponent(projectMembers);
        content.addComponent(footer);
        content.setExpandRatio(projectMembers, 1);
    }
    private Component buildProjectMembersTab() {
        final VerticalLayout root = new VerticalLayout();
        root.setSpacing(true);
        root.setMargin(new MarginInfo(false, true, false, true));
        root.setSizeFull();
        Label projectDetailsLabel = new Label("Project Name");
        projectDetailsLabel.addStyleName(ValoTheme.LABEL_H4);
        projectDetailsLabel.addStyleName(ValoTheme.LABEL_COLORED);
        TextField projectNameField = new TextField();
        projectNameField.setValue(projectName);
        Label projectMembersLabel = new Label("Project Members Privileges");
        projectMembersLabel.addStyleName(ValoTheme.LABEL_H4);
        projectMembersLabel.addStyleName(ValoTheme.LABEL_COLORED);
//////////Members table
        membersTable = new Table();
        membersTable.setWidth("100%");
        membersTable.addStyleName("multirowheaders");
        membersTable.addContainerProperty("Name", String.class, null);
        membersTable.addContainerProperty("Deleting project", CheckBox.class, null);
        membersTable.addContainerProperty("Managing sprints", CheckBox.class, null);
        membersTable.addContainerProperty("Adding members", CheckBox.class, null);
        membersTable.addContainerProperty("Deleting members", CheckBox.class, null);
        membersTable.addContainerProperty("Adding tasks", CheckBox.class, null);
        membersTable.addContainerProperty("Deleting tasks", CheckBox.class, null);
        membersTable.addContainerProperty("Delete Member", Button.class, null);
        membersTable.setColumnHeader("Deleting project", "<div>Deleting</div><br/>project");
        membersTable.setColumnHeader("Managing sprints", "<div>Managing</div><br/>sprints");
        membersTable.setColumnHeader("Adding members", "<div>Adding</div><br/>members");
        membersTable.setColumnHeader("Deleting members", "<div>Deleting</div><br/>members");
        membersTable.setColumnHeader("Adding tasks", "<div>Adding</div><br/>tasks");
        membersTable.setColumnHeader("Deleting tasks", "<div>Deleting</div><br/>tasks");
        membersTable.setColumnHeader("Delete Member", "<div>Delete</div><br/>member");
        membersTable.setColumnAlignment("Deleting project", Table.ALIGN_CENTER);
        membersTable.setColumnAlignment("Managing sprints", Table.ALIGN_CENTER);
        membersTable.setColumnAlignment("Adding members", Table.ALIGN_CENTER);
        membersTable.setColumnAlignment("Deleting members", Table.ALIGN_CENTER);
        membersTable.setColumnAlignment("Adding tasks", Table.ALIGN_CENTER);
        membersTable.setColumnAlignment("Deleting tasks", Table.ALIGN_CENTER);
        membersTable.setColumnAlignment("Delete Member", Table.ALIGN_CENTER);
        membersTable.setPageLength(6);
        membersTable.setSelectable(true);
        membersTable.setImmediate(true);
        fillTableWithProjectMembers();
//////////Adding user field
        HorizontalLayout addMemberContainer = new HorizontalLayout();
        addMemberContainer.setSpacing(true);
        final ComboBox addMemberBox = new ComboBox("Add new member:");
        addMemberBox.setInputPrompt("Choose..");
        addMemberBox.setWidth("300px");
        List<User> membersList = UserManagement.getUsers();
        ArrayList<String> appMembers = extractName(membersList);
        String[] appMembersStringList = new String[appMembers.size()];
        appMembersStringList = appMembers.toArray(appMembersStringList);
        int appMemberListSize = appMembersStringList.length;
        for (int i=0; i<appMemberListSize; i++){
            addMemberBox.addItem(appMembersStringList[i]);
        }
        Button addMemberButton = new Button("Add member", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                User u = UserManagement.getUser(addMemberBox.getValue().toString());
                thisProject.addUser(u);
                u.getProjectList().add(thisProject);
                Session session = HibernateSessionFactoryBean.getSession();
                try{
                    session.getTransaction().begin();
                    session.update(thisProject);
                    session.update(u);
                    session.getTransaction().commit();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    session.getTransaction().rollback();
                }
                finally{
                    session.close();
                }

                updateProjectMember(membersTable, addMemberBox.getValue().toString(), projectName);
            }
        });
        addMemberContainer.addComponent(addMemberBox);
        addMemberContainer.addComponent(addMemberButton);
        addMemberContainer.setComponentAlignment(addMemberButton, Alignment.BOTTOM_LEFT);
        root.addComponent(projectDetailsLabel);
        root.addComponent(projectNameField);
        root.addComponent(projectMembersLabel);
        root.addComponent(membersTable);
        root.addComponent(addMemberContainer);
        root.setExpandRatio(addMemberContainer, 1);
        root.setComponentAlignment(projectMembersLabel, Alignment.TOP_LEFT);
        root.setComponentAlignment(membersTable, Alignment.TOP_LEFT);
        root.setComponentAlignment(addMemberContainer, Alignment.TOP_LEFT);
        return root;
    }
    private void fillTableWithProjectMembers() {
        List<User> projectMembersList = thisProject.getUsers();
        ArrayList<String> projectMembers = extractName(projectMembersList);
        String[] projectMembersStringList = new String[projectMembers.size()];
        projectMembersStringList = projectMembers.toArray(projectMembersStringList);
        int projectMemberListSize = projectMembersStringList.length;
        for (int i = 0; i < projectMemberListSize; i++) {
            updateProjectMember(membersTable, projectMembersStringList[i], projectName);
        }
    }
    private ArrayList<String> extractName(List <User> members) {
        int l = members.size();
        ArrayList<String> Members = new ArrayList<String>();
        for(int u=0; u<l; u++) {
//Members.add(u,members.get(u).getFirstName()+" "+members.get(u).getLastName());
            Members.add(u,members.get(u).getEmail());
        }
        return Members;
    }
    private void updateMembers(){
        membersTable.removeAllItems();
        fillTableWithProjectMembers();
    }
    private void updateProjectMember(final Table membersTable, String memberName, final String projectName) {
//Adding user to table
        final User u = UserManagement.getUser(memberName);
        final Button deleteMemberButton = new Button("Delete");
        deleteMemberButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
            public void buttonClick(Button.ClickEvent event) {
                thisProject.removeUser(u);
                u.getProjectList().remove(thisProject);
                Session s = HibernateSessionFactoryBean.getSession();
                try{
                    s.getTransaction().begin();
                    s.update(u);
                    s.update(thisProject);
                    s.getTransaction().commit();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    s.getTransaction().rollback();
                }
                finally{
                    s.close();
                }
                updateMembers();
            }
        });
        deleteMemberButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        deleteMemberButton.setIcon(FontAwesome.TRASH_O);
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
        membersTable.addItem(new Object[]{memberName, deletingProjectCheckbox, managingSprintsCheckbox, addingMembersCheckbox,
                        deletingMembersCheckbox, addingTasksCheckbox, deletingTasksCheckbox, deleteMemberButton},
                null);
    }
    private Component buildFooter(final String projectName) {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        Button deleteButton = new Button("Delete Project");
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        Button updateButton = new Button("Update", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
//updateMembers(projectName);
                close();
                Notification success = new Notification(
                        "Project updated successfully");
                success.setDelayMsec(2000);
                success.setStyleName("bar success small");
                success.setPosition(Position.BOTTOM_CENTER);
                success.show(Page.getCurrent());
            }
        });
        footer.addComponent(updateButton);
        footer.addComponent(deleteButton);
        footer.setComponentAlignment(deleteButton, Alignment.TOP_LEFT);
        footer.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        return footer;
    }
}