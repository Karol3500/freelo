package org.freelo.controller.projects;

import java.util.Date;

import org.freelo.model.projects.Project;
import org.freelo.model.projects.ProjectManagement;
import org.freelo.model.projects.SprintDAO;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.ProjectManagement.AddSprintWindow;

public class AddSprintController {

	AddSprintWindow addSprintWindow;
	
	public AddSprintController(AddSprintWindow addSprintWindow) {
		this.addSprintWindow = addSprintWindow;
	}

	public Sprint persistSprint(String projectName, String sprintName, String projectManager, Date startDate, Date endDate) {
		User manager = UserManagement.getUser(projectManager);
		Project p = ProjectManagement.getProject(manager.getId(),projectName);
		if ( p == null)
			return null;
		Sprint s = new Sprint();
		s.setName(sprintName);
		s.setLeader(manager.getEmail());
		s.setEndDate(endDate);
		s.setStartDate(startDate);
		p.addSprint(s);
		SprintDAO.saveOrUpdateSprint(s);
		return s;
	}

}
