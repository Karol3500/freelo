package org.freelo.view.ProjectManagement;

import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.*;
import org.freelo.view.tasks.TaskPage;

import java.util.Date;

/**
 * Created by Konrad on 2015-01-13.
 */
public class SprintViewObject {
    public HorizontalLayout button = new HorizontalLayout();

    SprintViewObject(String ViewName, String SprintName, Date start_date, Date end_date) {

        init_button(ViewName, SprintName, start_date, end_date);
    }
    private void init_button(final String ViewName, String SprintName, Date start_date, Date end_date) {
        button.setSpacing(true);
        button.setStyleName("SprintButton");
        button.setWidth("100%");
        button.setHeight("41px");
        add_Labels(SprintName, start_date, end_date);
        //todo clickable layout
        button.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                new TaskPage();
                TaskPage.NAME = ViewName;
                final SprintViewType view = new SprintViewType(TaskPage.NAME, TaskPage.class);
                UI.getCurrent().getNavigator().navigateTo(view.getViewName());
            }
        });
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
