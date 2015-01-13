package org.freelo.view.ProjectManagement;

import com.vaadin.ui.*;

/**
 * Created by Konrad on 2015-01-13.
 */
public class ProjectItem extends ProjectManagementPage {
    public String manager;
    public Button sprintButton;

    public ProjectItem(String name, String manager) {

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
        Button addSprintButton = new Button("Add sprint..", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159879122119L;

            @Override
            public void buttonClick(Button.ClickEvent event) {

                createNewSprint = new addSprintWindow(nextcontainer);
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
        buttonsContainer.addComponent(manageProjectButton);
        buttonsContainer.addComponent(addSprintButton);


        nextcontainer.addComponent(buttonsContainer);

    }
}