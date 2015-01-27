package org.freelo.view.ProjectManagement;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.freelo.controller.projects.AddSprintController;
import org.freelo.model.projects.Project;
import org.freelo.model.projects.ProjectManagement;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;

import java.util.Date;

/**
 * Created by Konrad on 2015-01-13.
 */
public class AddSprintWindow extends Window {
	private static final long serialVersionUID = 5683290459141040269L;
	AddSprintController controller;
	public AddSprintWindow(final VerticalLayout nextcontainer, final String projectName, final String projectManager) {
		super("New sprint");
		controller = new AddSprintController(this);
		center();
		HorizontalLayout main = new HorizontalLayout();
		main.addStyleName("projectpopup");
		main.setSizeFull();
		setContent(main);
		setHeight("300px");
		setWidth("300px");
		setPositionY(50);
		setPositionX(50);
		final TextField sprintNameTextField = new TextField("Enter sprint name");
		sprintNameTextField.focus();
		// todo.add date
		final PopupDateField startDatePicker = new PopupDateField("Start date");
		final PopupDateField endDatePicker = new PopupDateField("End date");
        startDatePicker.setValue(new Date());
        endDatePicker.setValue(new Date());
		Button createButton = new Button("Create", new Button.ClickListener() {
			private static final long serialVersionUID = 2181474159749122119L;
			@Override
			public void buttonClick(Button.ClickEvent event) {
				Sprint s = controller.persistSprint(projectName,sprintNameTextField.getValue(),projectManager, startDatePicker.getValue(),endDatePicker.getValue());
				User manager = UserManagement.getUser(projectManager);
				Project p = ProjectManagement.getProject(manager.getId(), projectName);
				SprintViewObject sprint = new SprintViewObject(p, s);
				nextcontainer.addComponent(sprint.button);
				close();
			}
		});
		createButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		VerticalLayout itemplacement = new VerticalLayout();
		itemplacement.setSizeFull();
		itemplacement.addComponent(sprintNameTextField);
		itemplacement.addComponent(startDatePicker);
		itemplacement.addComponent(endDatePicker);
		itemplacement.addComponent(createButton);
		main.addComponent(itemplacement);
	}
}