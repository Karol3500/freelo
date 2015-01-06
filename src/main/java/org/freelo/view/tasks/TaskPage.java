package org.freelo.view.tasks;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
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
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jan on 2014-11-08.
 */
@Component
@Scope("prototype")
public class TaskPage extends HorizontalLayout implements View {

    private static final long serialVersionUID = -906274928780939032L;
    public static final String NAME = "";

    public List<CssLayout> columns;

    final HorizontalLayout container = new HorizontalLayout();
    final HorizontalLayout taskPanelContainer = new HorizontalLayout();

    final Panel todopanel = new Panel("TODO");
    final CssLayout todo = new CssLayout();

    final Panel ongoingpanel = new Panel("ON GOING");
    final CssLayout ongoing = new CssLayout();

    final Panel donepanel = new Panel("DONE");
    final CssLayout done = new CssLayout();

    String userName = null;

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
    DashboardMenu dashBoard;

    @Autowired
    DashboardMenuBean dashboardMenuBean;

    public TaskPage() {
        setSizeFull();
        addStyleName("taskpage");

        columns = new ArrayList<CssLayout>();

        container.addStyleName("container");
        container.setHeight("100%");
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
        com.vaadin.ui.Component files = buildBottomForm();

        final VerticalLayout pagePanel = new VerticalLayout();
        //pagePanel.setSizeFull();
        pagePanel.setMargin(new MarginInfo(true, true, true, true));
        pagePanel.setSpacing(true);
        pagePanel.setStyleName(ValoTheme.LAYOUT_WELL);
        pagePanel.setWidth("1200px");
        pagePanel.setHeight("100%");
        //loginPanel.addStyleName("login-panel");
        taskPanelContainer.setHeight("140%");
        taskPanelContainer.setWidth("100%");
        pagePanel.addComponent(taskPanelContainer);
        pagePanel.addComponent(files);

        pagePanel.setComponentAlignment(files, Alignment.BOTTOM_LEFT);
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

    public IndexedContainer updateFileContainer() {
        FileDAO fileDAO = new FileDAO();
        List<UserFile> files = fileDAO.getFilesByUserName(userName);
        if(files == null)
            return fileContainer;

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
                    Object someId = fileList.getValue();
                    fileList.removeItem(someId);
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

        final VerticalLayout bottomPanel = new VerticalLayout();
        bottomPanel.setHeight("60%");
        bottomPanel.setMargin(new MarginInfo(true, true, true, true));
        bottomPanel.setSpacing(true);
        bottomPanel.setStyleName(ValoTheme.LAYOUT_CARD);
        //loginPanel.addStyleName("login-panel");

        //Upload container
        FileUploader fileUploader = new FileUploader();

        Upload upload = new Upload("Attach files to the task", fileUploader);
        upload.setButtonCaption("Upload");
        upload.addSucceededListener(fileUploader);
        bottomPanel.addComponent(upload);
        //////////////////////////

        //Files table
        fileList.setContainerDataSource(fileContainer);
        fileList.setVisibleColumns(new String[] { FNAME, FUPLOAD, FSIZE, "Download", "Delete" });
        fileList.setSelectable(false);
        fileList.setImmediate(true);
        //fileList.setSizeFull();
        fileList.setPageLength(2);
        bottomPanel.addComponent(fileList);
        ///////////////////////////

        return bottomPanel;
    }

    @PostConstruct
    private void setup(){
        taskPanelContainer.addComponent(todopanel);
        taskPanelContainer.addComponent(ongoingpanel);
        taskPanelContainer.addComponent(donepanel);

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
        container.addComponent(buildPageForm());

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        userName = (String) VaadinSession.getCurrent().getAttribute("user");
        fileContainer = updateFileContainer();
    }

}

