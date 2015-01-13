package org.freelo.controller.projects;

import org.freelo.model.projects.Project;
import org.freelo.model.projects.ProjectManagement;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.ProjectManagement.ProjectItem;
import org.freelo.view.ProjectManagement.Subwindow;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by karol on 13.01.15.
 */
public class CreateProjectSubwindowController {
    Subwindow subwindow;
    public CreateProjectSubwindowController(Subwindow s){
        this.subwindow = s;

    }

    public void createProject(ProjectItem pi) {
        Project pr = new Project();
        buildProjectFromProjectItem(pi, pr);
        ProjectManagement.addProject(pr);
    }

    private void buildProjectFromProjectItem(ProjectItem pi, Project pr) {
        pi.sprintButton.addClickListener(new SprintCreationListener(pr));
        pr.setStart(new Date());
        pr.setEnd(new Date());
        User theOneThatCreates = UserManagement.getUser(pi.manager);
        pr.setManager(theOneThatCreates.getId());
        pr.addUser(theOneThatCreates);
        pr.setSprints(new ArrayList<Sprint>());
    }
}
