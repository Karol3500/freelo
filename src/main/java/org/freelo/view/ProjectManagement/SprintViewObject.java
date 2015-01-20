package org.freelo.view.ProjectManagement;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.freelo.view.SimpleLoginUI;
import org.freelo.view.tasks.TaskPage;

import java.util.Date;

/**
 * Created by Konrad on 2015-01-13.
 */
public class SprintViewObject {
    public HorizontalLayout button = new HorizontalLayout();
    private String ViewName;
    private String SprintName;
    private Date start_date;
    private Date end_date;
    public TaskPage TP = new TaskPage();
    public Navigator navi = new Navigator(UI.getCurrent(), UI.getCurrent());

    SprintViewObject(String ViewName, String SprintName, Date start_date, Date end_date) {
        this.ViewName = ViewName;
        this.TP.change_task_name(this.ViewName);
        this.SprintName = SprintName;
        this.start_date = start_date;
        this.end_date = end_date;
        init_button();
        navi.addView(this.ViewName, TP);
    }
    private void init_button() {
        button.setSpacing(true);
        button.setStyleName("SprintButton");
        button.setWidth("100%");
        button.setHeight("41px");
        add_Labels();
        //todo clickable layout
        button.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                navi.navigateTo(ViewName);
            }
        });
    }

    private void add_Labels(){
        Label Sprint_Name = new Label(this.SprintName);
        Label Sprint_Start = new Label("Start: "+this.start_date);
        Label Sprint_End = new Label("End: "+this.end_date);
        button.addComponent(Sprint_Name);
        button.addComponent(Sprint_Start);
        button.addComponent(Sprint_End);
        button.setComponentAlignment(Sprint_Name, Alignment.MIDDLE_LEFT);
        button.setComponentAlignment(Sprint_Start, Alignment.MIDDLE_CENTER);
        button.setComponentAlignment(Sprint_End, Alignment.MIDDLE_RIGHT);
    }
}
