package org.freelo.view.tasks;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.themes.ValoTheme;
import org.freelo.controller.tasks.TaskPageController;
import org.freelo.model.files.FileDAO;
import org.freelo.model.files.FileManagement;
import org.freelo.model.files.FileUploader;
import org.freelo.model.files.UserFile;
import org.freelo.view.Dashboard.DashboardMenu;
import org.freelo.view.Dashboard.DashboardMenuBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * Created by Jan on 2014-11-08.
 */
@Component
@Scope("prototype")
public class TaskPage extends HorizontalLayout implements View {

    private static final long serialVersionUID = -906274928780939032L;
    public static final String NAME = "";
    public Button shoutButton;
    public TextField shoutField;
    public TextArea shoutBoxArea;

    public TaskPageController taskPageController;

    public List<VerticalLayout> columns;

    final HorizontalLayout container = new HorizontalLayout();
    final HorizontalLayout taskPanelContainer = new HorizontalLayout();

    final Panel todopanel = new Panel("TODO");
    final VerticalLayout todo = new VerticalLayout();

    final Panel ongoingpanel = new Panel("ON GOING");
    final VerticalLayout ongoing = new VerticalLayout();

    final Panel donepanel = new Panel("DONE");
    final VerticalLayout done = new VerticalLayout();

    public String userName = null;

    // fields for bottom container
    private Table fileList = new Table();

    private static final String FNAME = "File Name";
    private static final String FUPLOAD = "Uploaded by";
    private static final String FSIZE = "File Size (B)";
    private static final String[] fieldNames = new String[] { FNAME, FUPLOAD,
            FSIZE, "File Path" };

    FileManagement fileManagement = new FileManagement();
    IndexedContainer fileContainer = createFileContainer();
    ////////////////////////

    TabSheet tabSheet = new TabSheet();
    DashboardMenu dashBoard;

    @Autowired
    DashboardMenuBean dashboardMenuBean;

    public TaskPage() {
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

        @Override
        public void uploadSucceeded(Upload.SucceededEvent event) {
            updateFileContainer();
        }
    }
//
//    class FileDeletedListener implements Button.ClickListener{
//        long fileId;
//        public FileDeletedListener(long fileId){
//            this.fileId = fileId;
//        }
//
//        @Override
//        public void buttonClick(Button.ClickEvent event) {
//
//        }
//    }

    public IndexedContainer updateFileContainer() {
        FileDAO fileDAO = new FileDAO();
        List<UserFile> files = fileDAO.getFilesByUserName(userName);

        if(files == null)
            return fileContainer;
        fileContainer.removeAllItems();
        fileContainer.removeAllContainerFilters();

        for (UserFile f : files) {
            //System.out.println(f.get_fileName());

            final Button deleteFile = new Button("Delete");
            final Button downloadFile = new Button("Download");

            deleteFile.setStyleName(ValoTheme.BUTTON_DANGER);
            downloadFile.setStyleName(ValoTheme.BUTTON_PRIMARY);
            final long fileId = f.getId();

            deleteFile.addClickListener(new Button.ClickListener() {
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
        //////////////////////////
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addTab(buildTab1(), "ShoutBox");
        tabSheet.addTab(buildTab2(), "Task Files");
        tabSheet.setSizeFull();
        //bottomPanel.addComponent(fileList);
        ///////////////////////////
        return bottomPanel;
    }

    private com.vaadin.ui.Component buildSideForm(){
        final VerticalLayout sideLayout = new VerticalLayout();
        sideLayout.setSpacing(true);
        sideLayout.setHeight("100%");
        sideLayout.setWidth("400px");

        Panel projectMembersPanel = new Panel("Project Members");
        VerticalLayout projectMembersLayout = new VerticalLayout();
        projectMembersPanel.setContent(projectMembersLayout);
        projectMembersPanel.setStyleName("ProjectMembersPanel");
        projectMembersPanel.setHeight("100%");
        projectMembersPanel.setWidth("100%");

        //controller for project/sprint page needed
        //Date startdate = startDatePicker.getValue();
        //Date enddate =  endDatePicker.getValue();

        Date startDate = new Date(2015, 0, 1);
        Date endDate =  new Date(2015, 0, 14);
        CalendarPanel calendarPanel = new CalendarPanel(startDate, endDate);

        sideLayout.addComponent(calendarPanel);
        sideLayout.addComponent(projectMembersPanel);
        sideLayout.setExpandRatio(projectMembersPanel, 1f);
        return sideLayout;
    }

    VerticalLayout buildTab2(){
        VerticalLayout tab2 = new VerticalLayout();
        tab2.setSpacing(true);
        tab2.setMargin(new MarginInfo(true, true, true, true));
        //Upload container
        FileUploader fileUploader = new FileUploader();

        Upload upload = new Upload("Attach files to the project", fileUploader);
        upload.setButtonCaption("Upload");
        upload.addSucceededListener(fileUploader);
        upload.addSucceededListener(new UploadSuccededListener());

        //Files table
        fileList.setContainerDataSource(fileContainer);
        fileList.setVisibleColumns(new String[]{FNAME, FUPLOAD, FSIZE, "Download", "Delete"});
        fileList.setSelectable(false);
        fileList.setImmediate(true);
        fileList.setWidth("100%");
        //fileList.setColumnAlignments(Table.Align.CENTER);

        //fileList.setSizeFull();
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

    @PostConstruct
    private void setup(){

        taskPanelContainer.addComponent(todopanel);
        taskPanelContainer.addComponent(ongoingpanel);
        taskPanelContainer.addComponent(donepanel);

        taskPanelContainer.setSpacing(true);
        columns.add(todo);
        columns.add(ongoing);
        columns.add(done);

        dashBoard = dashboardMenuBean.getNewDashboardMenu();
        final Button addComponentButton = new Button("Add Task");
        addComponentButton.addStyleName("button1");
        addComponentButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749123339L;

            public void buttonClick(Button.ClickEvent event) {
                TaskCreationWindow taskCreationWindow=
                        new TaskCreationWindow(columns, userName);
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


        container.setExpandRatio(pagePanel, 1f);


        taskPageController = new TaskPageController(this);
    }

    public class CalendarPanel extends HorizontalLayout{

        public CalendarPanel(Date startdate, Date enddate){
            final Panel calPanel = new Panel("Sprint Calendar");
            calPanel.setStyleName("Calendar");
            calPanel.setWidth("400px");
            calPanel.setHeight("350px");

            Calendar cal = new Calendar();
            cal.setWidth("100%");
            cal.setHeight("100%");
            cal.setFirstVisibleDayOfWeek(2);
            cal.setLastVisibleDayOfWeek(6);
            calPanel.setContent(cal);
            cal.setLocale(new Locale("en", "US"));


            cal.setStartDate(startdate);
            cal.setEndDate(enddate);


            BasicEvent startDateEvent = new BasicEvent("Start",
                    "Sprint start date",
                    startdate, startdate);
            BasicEvent endDateEvent = new BasicEvent("End",
                    "Sprint end date",
                    enddate, enddate);
            startDateEvent.setAllDay(true);
            endDateEvent.setAllDay(true);

            cal.addEvent(startDateEvent);
            cal.addEvent(endDateEvent);

            addComponent(calPanel);
        }
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        userName = (String) VaadinSession.getCurrent().getAttribute("user");
        fileContainer = updateFileContainer();
    }

}

