package org.freelo.controller.projects;


import org.freelo.model.projects.Project;
import org.freelo.model.projects.ProjectManagement;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.ProjectManagement.ProjectItem;
import org.freelo.view.ProjectManagement.ProjectManagementPage;

import java.util.List;

/**
 * Created by karol on 13.01.15.
 */
public class ProjectManagementPageController {

    private ProjectManagementPage projectPage;
    public ProjectManagementPageController(ProjectManagementPage p){
        projectPage = p;
    }

    public void populateProjectPageAfterLogin(String userName){
        User u = UserManagement.getUser(userName);
        List<Project> existingProjectsForUser = ProjectManagement.getProjects(u);

        if(existingProjectsForUser==null)
            return;
        for(Project proj : existingProjectsForUser){
            ProjectItem p = projectPage.addProject(UserManagement.
            		getUser(proj.getManager()).getEmail(),proj.getName());
            populateSprintButtons(p,proj);
        }
    }

	private void populateSprintButtons(ProjectItem p, Project proj) {
		for(Sprint s : proj.getSprints()){
			p.addSprint(s,proj.getName());
		}
	}
}
