package org.freelo.view.ProjectManagement;

import com.vaadin.ui.*;

import java.util.Date;

/**
 * Created by Konrad on 2015-01-13.
 */
public class SprintViewObject {
//    public final Button SprintButton;
//    public TaskPage taskPageView;
//    SprintViewObject() {
//        SprintButton = new Button("Sprint", new Button.ClickListener() {
//            private static final long serialVersionUID = 2181474159879122119L;
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                getUI().getNavigator().navigateTo(TaskPage.NAME);
//            }
//        });
//    }

    public HorizontalLayout button = new HorizontalLayout();
    SprintViewObject(String SprintName, Date start_date, Date end_date) {
        init_button(SprintName, start_date, end_date);
    }

    private void init_button(String SprintName, Date start_date, Date end_date) {
        button.setSpacing(true);
        button.setStyleName("SprintButton");
        button.setWidth("100%");
        button.setHeight("35px");
        add_Labels(SprintName, start_date, end_date);
        //todo clickable layout
//        button.addLayoutClickListener();
    }

    private void add_Labels(String SprintName, Date start_date, Date end_date){
        Label Sprint_Name = new Label(SprintName);
        Label Sprint_Start = new Label("Start: "+start_date);
        Label Sprint_End = new Label("End: "+end_date);
        button.addComponent(Sprint_Name);
        button.addComponent(Sprint_Start);
        button.addComponent(Sprint_End);
        button.setComponentAlignment(Sprint_Name, Alignment.MIDDLE_LEFT);
        button.setComponentAlignment(Sprint_Start, Alignment.MIDDLE_CENTER);
        button.setComponentAlignment(Sprint_End, Alignment.MIDDLE_RIGHT);
    }
}
