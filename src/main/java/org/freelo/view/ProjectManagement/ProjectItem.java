package org.freelo.view.ProjectManagement;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.freelo.model.users.User;

/**
 * Created by Konrad on 2015-01-13.
 */
public class ProjectItem extends ProjectManagementPage {
	private static final long serialVersionUID = 1L;
	public String manager;
    public Button sprintButton;
    public String name;

    private User getCurrentUser() {
        return (User) VaadinSession.getCurrent().getAttribute("userClass");
    }
    final User user = getCurrentUser();

    public ProjectItem(final String name, String manager) {

    	this.manager = manager;
    	this.name = name;
        setSizeFull();
        HorizontalLayout container = new HorizontalLayout();
        final VerticalLayout nextcontainer = new VerticalLayout();
        nextcontainer.addStyleName("projectContainer");
        Panel ProjectPanel = new Panel("Project: "+name);

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
        final User user = getCurrentUser();
		manageProject = new ManageProjectWindow(user, name);
		UI.getCurrent().addWindow(manageProject);
	}
}