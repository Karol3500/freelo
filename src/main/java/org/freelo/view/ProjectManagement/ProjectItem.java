package org.freelo.view.ProjectManagement;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Konrad on 2015-01-13.
 */
public class ProjectItem extends ProjectManagementPage {
	private static final long serialVersionUID = 1L;
	public String manager;
    public Button sprintButton;
    public String name;

    public ProjectItem(final String name, String manager) {
    	this.manager = manager;
    	this.name = name;
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
                createNewSprint = new AddSprintWindow(nextcontainer, name);
                UI.getCurrent().addWindow(createNewSprint);
            }
        });

        Button manageProjectButton = new Button("Manage project", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                displayProjectCreationPopup();
            }
        });
        container.addComponent(ProjectPanel);

        final HorizontalLayout buttonsContainer = new HorizontalLayout();
        buttonsContainer.setSpacing(true);
        buttonsContainer.addComponent(manageProjectButton);
        buttonsContainer.addComponent(sprintButton);
        nextcontainer.addComponent(buttonsContainer);

    }

	void displayProjectCreationPopup() {
		manageProject = new ManageProjectWindow();
		UI.getCurrent().addWindow(manageProject);
	}
}