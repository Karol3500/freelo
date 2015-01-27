package org.freelo.view.ProjectManagement;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.freelo.model.projects.Project;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.tasks.Note;
import org.freelo.view.tasks.TaskCard;
import org.freelo.view.tasks.TaskPage;

import java.util.Date;

/**
 * Created by Konrad on 2015-01-13.
 */
public class SprintViewObject {
    public HorizontalLayout button = new HorizontalLayout();
    private String viewName;
    private String projectName;
    private String sprintName;
    private Date startDate;
    private Date endDate;
    private Project project;
    private Sprint sprint;
    private TaskPage tp;

    public Navigator navi = UI.getCurrent().getNavigator();

    public SprintViewObject(Project p, Sprint s){
        project =p;
        sprint = s;
        this.projectName = p.getName();
        this.sprintName = s.getName();
        this.viewName =projectName+"_"+sprintName;
        this.startDate = s.getStartDate();
        this.endDate = s.getEndDate();
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
            if(tp!=null){
                navi.navigateTo(viewName);
            }
            tp = getTaskPageFromDb();
            if(tp==null){
                tp = new TaskPage(sprint);
                tp.change_task_name(viewName);
            }
	        navi.addView(viewName, tp);
            navi.navigateTo(viewName);
        }
    }

    private TaskPage getTaskPageFromDb() {
        if(project == null || sprint == null){
            return null;
        }
        TaskPage tp = new TaskPage(sprint);
        for(Note n : sprint.getToDo()){
            TaskCard tc = new TaskCard(n.getTaskName(), n.getPriority(), n.getText(),sprint);
            tp.getTaskCreationWindowWithoutController().createTask(tc,0);
        }

        for(Note n : sprint.getOnGoing()){
            TaskCard tc = new TaskCard(n.getTaskName(), n.getPriority(), n.getText(),sprint);
            tp.getTaskCreationWindowWithoutController().createTask(tc,1);
        }

        for(Note n : sprint.getDone()){
            TaskCard tc = new TaskCard(n.getTaskName(), n.getPriority(), n.getText(),sprint);
            tp.getTaskCreationWindowWithoutController().createTask(tc,2);
        }

        return tp;
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
