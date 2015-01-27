package org.freelo.view.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.freelo.controller.tasks.TaskPageController;
import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.files.FileDAO;
import org.freelo.model.files.FileManagement;
import org.freelo.model.files.FileUploader;
import org.freelo.model.files.UserFile;
import org.freelo.model.projects.Project;
import org.freelo.model.projects.ProjectManagement;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.ProjectManagement.SprintViewObject;
import org.freelo.view.SimpleLoginUI;
import org.freelo.view.Dashboard.DashboardMenu;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.themes.ValoTheme;
import org.hibernate.Session;


/**
 * Created by Jan on 2014-11-08.
 */
public class TaskPage extends HorizontalLayout implements View {

    private static final long serialVersionUID = -906274928780939032L;
    public static String NAME = "";
    public Button shoutButton;
    public TextField shoutField;
    public TextArea shoutBoxArea;
    public TaskPageController taskPageController;

    public List<VerticalLayout> columns;
    public void change_task_name(String new_name){
        this.NAME = new_name;
    }

    final HorizontalLayout container = new HorizontalLayout();
    final HorizontalLayout taskPanelContainer = new HorizontalLayout();

    final Panel todopanel = new Panel("TODO");
    final VerticalLayout todo = new VerticalLayout();

    final Panel ongoingpanel = new Panel("ON GOING");
    final VerticalLayout ongoing = new VerticalLayout();

    final Panel donepanel = new Panel("DONE");
    final VerticalLayout done = new VerticalLayout();

    public Button createEventButton;
    public String userName = null;
    User user = null;

    // fields for bottom container
    private Table fileList = new Table();

    private static final String FNAME = "File Name";
    private static final String FUPLOAD = "Uploaded by";
    private static final String FSIZE = "File Size (B)";
    private static final String[] fieldNames = new String[] { FNAME, FUPLOAD,
            FSIZE, "File Path" };

    FileManagement fileManagement = new FileManagement();
    IndexedContainer fileContainer = createFileContainer();
    Calendar cal = new Calendar();
    String projectManager;
    String projectName;
    ////////////////////////
    public Sprint sprint;

    TabSheet tabSheet = new TabSheet();
    DashboardMenu dashBoard;

    public TaskPage(Sprint sprint) {
        this.sprint = sprint;
        Session session = HibernateSessionFactoryBean.getSession();
        try {
            session.getTransaction().begin();
            Project pr = sprint.getProject();
            this.projectName = pr.getName();
            this.projectManager = UserManagement.getUser(pr.getManager()).getEmail();
        }
        catch(Exception ex){}
        finally{
            session.close();
        }


        setSizeFull();
        addStyleName("taskpage");

        columns = new ArrayList<VerticalLayout>();

        container.addStyleName("container");
        container.setSizeFull();
        addComponent(container);

        todo.addStyleName("content");
        todopanel.addStyleName("todopanel");
        todopanel.setHeight("100%");
        todopanel.setContent(todo);

        // ONGOING
        ongoing.addStyleName("content");
        ongoingpanel.addStyleName("ongoingpanel");
        ongoingpanel.setHeight("100%");
        ongoingpanel.setContent(ongoing);

        // DONE
        done.addStyleName("content");
        donepanel.addStyleName("donepanel");
        donepanel.setHeight("100%");
        donepanel.setContent(done);
        setup();
    }

    private com.vaadin.ui.Component buildPageForm(){
        com.vaadin.ui.Component bottomPanel = buildBottomForm();

        final VerticalLayout pagePanel = new VerticalLayout();
        pagePanel.setSizeFull();
        //pagePanel.setMargin(new MarginInfo(false, true, false, true));
        pagePanel.setSpacing(true);
        pagePanel.setStyleName(ValoTheme.LAYOUT_WELL);

        taskPanelContainer.setHeight("100%");
        taskPanelContainer.setWidth("100%");
        pagePanel.addComponent(taskPanelContainer);
        pagePanel.addComponent(bottomPanel);

        pagePanel.setExpandRatio(taskPanelContainer, 2);
        pagePanel.setExpandRatio(bottomPanel, 1);
        pagePanel.setComponentAlignment(bottomPanel, Alignment.BOTTOM_LEFT);
        return pagePanel;
    }

    public IndexedContainer createFileContainer() {
        IndexedContainer ic = new IndexedContainer();

        for (String p : fieldNames) {
            ic.addContainerProperty(p, String.class, "");
        }

        ic.addContainerProperty("Download", Button.class, null);
        ic.addContainerProperty("Delete", Button.class, null);

        return ic;

    }

    class UploadSuccededListener implements Upload.SucceededListener {
		private static final long serialVersionUID = 1L;

		@Override
        public void uploadSucceeded(Upload.SucceededEvent event) {
            updateFileContainer();
        }
    }

    @SuppressWarnings("unchecked")
	public IndexedContainer updateFileContainer() {
        List<UserFile> files = FileDAO.getFilesByProjectName(sprint.getName());

        if(files == null)
            return fileContainer;
        fileContainer.removeAllItems();
        fileContainer.removeAllContainerFilters();

        for (UserFile f : files) {
            final Button deleteFile = new Button("Delete");
            final Button downloadFile = new Button("Download");

            deleteFile.setStyleName(ValoTheme.BUTTON_DANGER);
            downloadFile.setStyleName(ValoTheme.BUTTON_PRIMARY);
            final long fileId = f.getId();

            deleteFile.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(Button.ClickEvent event) {
                    fileManagement.deleteFile(fileId);
                    updateFileContainer();
                }
            });

            fileManagement.downloadFile(fileId).extend(downloadFile);

            Object id = fileContainer.addItem();
            fileContainer.getContainerProperty(id, FNAME).setValue(f.getFileName());
            fileContainer.getContainerProperty(id, FUPLOAD).setValue(f.getUserName());
            fileContainer.getContainerProperty(id, FSIZE).setValue(String.valueOf(f.getFileSize()));
            fileContainer.getContainerProperty(id, "File Path").setValue(f.getFilePath());
            fileContainer.getContainerProperty(id, "Download").setValue(downloadFile);
            fileContainer.getContainerProperty(id, "Delete").setValue(deleteFile);

        }

        return fileContainer;

    }

    private com.vaadin.ui.Component buildBottomForm(){
        VerticalLayout bottomPanel = new VerticalLayout();

        bottomPanel.addComponent(tabSheet);
        bottomPanel.setSizeFull();
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addTab(buildTab1(), "ShoutBox");
        tabSheet.addTab(buildTab2(), "Task Files");
        tabSheet.setSizeFull();
        return bottomPanel;
    }
    private com.vaadin.ui.Component buildMembersTable(){
        Table membersTable = new Table();
        membersTable.setWidth("100%");
        membersTable.addContainerProperty("Name", String.class, null);

        fillTableWithProjectMembers(projectName, projectManager, membersTable);
        return membersTable;
    }
    private void fillTableWithProjectMembers(String name, String manager, Table membersTable) {
        int managerID = UserManagement.getUserID(manager);
        Project proj = ProjectManagement.getProject(managerID, name);
        List<User> projectMembersList = proj.getUsers();
        ArrayList<String> projectMembers = extractName(projectMembersList);
        String[] projectMembersStringList = new String[projectMembers.size()];
        projectMembersStringList = projectMembers.toArray(projectMembersStringList);
        int projectMemberListSize = projectMembersStringList.length;
        for (int i = 0; i < projectMemberListSize; i++) {
            membersTable.addItem(new Object[]{projectMembersStringList[i]}, null);
        }
    }
    private ArrayList<String> extractName(List <User> members) {
        int l = members.size();
        ArrayList<String> Members = new ArrayList<String>();
        for(int u=0; u<l; u++) {
            Members.add(u,members.get(u).getFirstName()+" "+members.get(u).getLastName());
        }
        return Members;
    }

    @SuppressWarnings("deprecation")
	private com.vaadin.ui.Component buildSideForm(){
        final VerticalLayout sideLayout = new VerticalLayout();
        sideLayout.setSpacing(true);
        sideLayout.setHeight("100%");
        //sideLayout.setWidth("400px");

        Panel projectMembersPanel = new Panel("Project Members");
        VerticalLayout projectMembersLayout = new VerticalLayout();
        projectMembersPanel.setContent(projectMembersLayout);
        projectMembersPanel.setStyleName("ProjectMembersPanel");
        projectMembersPanel.setHeight("100%");
        projectMembersPanel.setWidth("100%");
        projectMembersLayout.addComponent(buildMembersTable());

        final Panel calPanel = buildCalendar();

        final Button setMonthButton = new Button("Monthly View");
        setMonthButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749123339L;

            public void buttonClick(Button.ClickEvent event) {
                setMonthView();
            }
        });

        createEventButton = new Button("Create New Event");

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.addComponents(createEventButton, setMonthButton);
        sideLayout.addComponents(calPanel, buttons);
        sideLayout.addComponent(projectMembersPanel);
        sideLayout.setExpandRatio(projectMembersPanel, 1f);
        return sideLayout;
    }

    private Panel buildCalendar() {
        final Panel calPanel = new Panel("Sprint Calendar" + sprint.getName());
        calPanel.setStyleName("Calendar");
        cal.setWidth("100%");
        cal.setHeight("100%");

        cal.setLocale(new Locale("en", "US"));

        Date startdate = new Date(115, 0, 10);
        Date enddate =  new Date(115, 0, 30);
        cal.setStartDate(startdate);
        cal.setEndDate(enddate);
        cal.setFirstVisibleDayOfWeek(2);
        cal.setLastVisibleDayOfWeek(6);

        addSprintEvent(sprint.getName());

        calPanel.setContent(cal);

        return calPanel;
    }

    private void addSprintEvent(String sprintName){
        addEvent("Start", "Sprint " + sprintName + " start date", sprint.getStartDate(), true);
        addEvent("End", "Sprint " + sprintName + " end date", sprint.getEndDate(), true);

    }
    public void addEvent(String eventName, String eventDescription, Date eventDate, boolean allDay){
        BasicEvent newEvent = new BasicEvent(eventName, eventDescription, eventDate);
        newEvent.setAllDay(allDay);

        cal.addEvent(newEvent);
    }

    private void setMonthView(){
        Date startdate = new Date(115, 0, 10);
        Date enddate =  new Date(115, 0, 30);
        cal.setStartDate(startdate);
        cal.setEndDate(enddate);
    }

    VerticalLayout buildTab2(){
        VerticalLayout tab2 = new VerticalLayout();
        tab2.setSpacing(true);
        tab2.setMargin(new MarginInfo(true, true, true, true));
        //Upload container
        FileUploader fileUploader = new FileUploader(sprint.getName());

        Upload upload = new Upload("Attach files to the project", fileUploader);
        upload.setButtonCaption("Upload");
        upload.addSucceededListener(fileUploader);
        upload.addSucceededListener(new UploadSuccededListener());

        //Files table
        fileList.setContainerDataSource(fileContainer);
        fileList.setVisibleColumns(new Object[]{FNAME, FUPLOAD, FSIZE, "Download", "Delete"});
        fileList.setSelectable(false);
        fileList.setImmediate(true);
        fileList.setWidth("100%");
        fileList.setPageLength(3);

        tab2.addComponents(upload, fileList);

        return tab2;
    }

    VerticalLayout buildTab1(){
        VerticalLayout tab1 = new VerticalLayout();
        tab1.setSizeFull();
        tab1.setSpacing(true);
        tab1.setMargin(new MarginInfo(true, true, false, true));

        shoutBoxArea = new TextArea();
        //shoutBoxArea.setReadOnly(true);
        shoutBoxArea.setEnabled(false);
        shoutBoxArea.setSizeFull();
        shoutBoxArea.setWordwrap(true);

        HorizontalLayout writeTextArea = buildWritingArea();
        writeTextArea.setWidth("90%");
        tab1.addComponents(shoutBoxArea, writeTextArea);
        tab1.setExpandRatio(shoutBoxArea, 2.0f);
        tab1.setExpandRatio(writeTextArea, 1.0f);

        return tab1;
    }

    HorizontalLayout buildWritingArea(){
        HorizontalLayout writingArea = new HorizontalLayout();
        writingArea.setSpacing(true);

        shoutField = new TextField();
        shoutButton = new Button("Shout");

        shoutField.setWidth("100%");
        shoutField.setValue("");
        shoutField.setNullRepresentation("");
        shoutField.setInputPrompt("Shout HERE...");
        shoutField.setImmediate(true);

        shoutButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        shoutButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        writingArea.addComponents(shoutField, shoutButton);

        return writingArea;
    }

    public TaskCreationWindow getTaskCreationWindowWithoutController(Sprint sprint){
        TaskCreationWindow tcw = new TaskCreationWindow(columns,userName,sprint);
        tcw.controller = null;
        return tcw;
    }

    private void setup(){

        taskPanelContainer.addComponent(todopanel);
        taskPanelContainer.addComponent(ongoingpanel);
        taskPanelContainer.addComponent(donepanel);

        taskPanelContainer.setSpacing(true);
        columns.add(todo);
        columns.add(ongoing);
        columns.add(done);

        dashBoard = new DashboardMenu();
        dashBoard.setupUI((SimpleLoginUI)getUI());
        final Button addComponentButton = new Button("Add Task");
        addComponentButton.addStyleName("button1");
        addComponentButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749123339L;

            public void buttonClick(Button.ClickEvent event) {
                TaskCreationWindow taskCreationWindow=
                        new TaskCreationWindow(columns, userName,sprint);
                UI.getCurrent().addWindow(taskCreationWindow);
            }
        });
        //adding elements to containers

        todo.addComponent(addComponentButton);
        container.addComponent(dashBoard);


        VerticalLayout pagePanel = (VerticalLayout) buildPageForm();
        VerticalLayout sidePanel = (VerticalLayout) buildSideForm();

        container.addComponent(pagePanel);
        container.addComponent(sidePanel);
        container.setSpacing(true);


        container.setExpandRatio(pagePanel, 3f);
        container.setExpandRatio(sidePanel, 1f);

        taskPageController = new TaskPageController(this);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        user = (User) VaadinSession.getCurrent().getAttribute("userClass");
        userName = user.getEmail();

        fileContainer = updateFileContainer();
    }

}

