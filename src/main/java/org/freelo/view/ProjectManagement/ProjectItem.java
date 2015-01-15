package org.freelo.view.ProjectManagement;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.freelo.model.projects.Project;

/**
 * Created by Konrad on 2015-01-13.
 */
public class ProjectItem extends ProjectManagementPage {
    public String manager;
    public Button sprintButton;

    public ProjectItem(final String name, String manager) {
    	this.manager = manager;
        setSizeFull();
        HorizontalLayout container = new HorizontalLayout();
        final VerticalLayout nextcontainer = new VerticalLayout();
        nextcontainer.addStyleName("projectContainer");
        Panel ProjectPanel = new Panel(name);

        ProjectPanel.addStyleName("projectPanel");
        ProjectPanel.setWidth("100%");
        ProjectPanel.setContent(nextcontainer);
        container.setWidth("100%");
        addComponent(container);
        sprintButton = new Button("Add sprint..", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159879122119L;

            @Override
            public void buttonClick(Button.ClickEvent event) {

                createNewSprint = new addSprintWindow(nextcontainer, name);
                UI.getCurrent().addWindow(createNewSprint);

            }


        });

        Button manageProjectButton = new Button("Manage project", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //Method to display project creation popup called here
                manageProject = new manageProjectWindow();
                UI.getCurrent().addWindow(manageProject);
            }
        });
        container.addComponent(ProjectPanel);

        final HorizontalLayout buttonsContainer = new HorizontalLayout();
        buttonsContainer.setSpacing(true);
        buttonsContainer.addComponent(manageProjectButton);
        buttonsContainer.addComponent(sprintButton);


        nextcontainer.addComponent(buttonsContainer);

    }
}