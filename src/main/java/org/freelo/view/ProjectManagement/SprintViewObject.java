package org.freelo.view.ProjectManagement;

import java.util.Date;

import org.freelo.view.tasks.TaskPage;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

/**
 * Created by Konrad on 2015-01-13.
 */
public class SprintViewObject {
    public HorizontalLayout button = new HorizontalLayout();
    private String viewName;
    private String sprintName;
    private Date startDate;
    private Date endDate;
    public Navigator navi = UI.getCurrent().getNavigator();

    SprintViewObject(String projectName, String sprintName, Date start_date, Date end_date) {
        this.viewName = projectName+"_"+sprintName;
        this.sprintName = sprintName;
        this.startDate = start_date;
        this.endDate = end_date;
        init_button();
    }
    private void init_button() {
        button.setSpacing(true);
        button.setStyleName("SprintButton");
        button.setWidth("100%");
        button.setHeight("41px");
        add_Labels();
        //todo clickable layout
        button.addLayoutClickListener(new goToSprintClickListener());
    }
    
    private class goToSprintClickListener implements LayoutEvents.LayoutClickListener{
		private static final long serialVersionUID = 1L;

		@Override
        public void layoutClick(LayoutEvents.LayoutClickEvent event) {
    		TaskPage tp = new TaskPage(sprintName, startDate, endDate);
    		tp.change_task_name(viewName);
	        navi.addView(viewName, tp);
            navi.navigateTo(viewName);
        }
    }

    private void add_Labels(){
        Label Sprint_Name = new Label(this.sprintName);
        Label Sprint_Start = new Label("Start: "+this.startDate);
        Label Sprint_End = new Label("End: "+this.endDate);
        button.addComponent(Sprint_Name);
        button.addComponent(Sprint_Start);
        button.addComponent(Sprint_End);
        button.setComponentAlignment(Sprint_Name, Alignment.MIDDLE_LEFT);
        button.setComponentAlignment(Sprint_Start, Alignment.MIDDLE_CENTER);
        button.setComponentAlignment(Sprint_End, Alignment.MIDDLE_RIGHT);
    }
}
