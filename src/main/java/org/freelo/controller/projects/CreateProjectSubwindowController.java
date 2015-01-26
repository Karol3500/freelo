package org.freelo.controller.projects;

import java.util.ArrayList;

import org.freelo.model.projects.Project;
import org.freelo.model.projects.ProjectManagement;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.freelo.view.ProjectManagement.ProjectItem;
import org.freelo.view.ProjectManagement.Subwindow;

/**
 * Created by karol on 13.01.15.
 */
public class CreateProjectSubwindowController {
    Subwindow subwindow;
    public CreateProjectSubwindowController(Subwindow s){
        this.subwindow = s;

    }

    public void createProject(ProjectItem pi) {
        Project pr = buildProjectFromProjectItem(pi);
        ProjectManagement.addProject(pr);
    }

    private Project buildProjectFromProjectItem(ProjectItem pi) {
    	Project pr = new Project();
        User theOneThatCreates = UserManagement.getUser(pi.manager);
        pr.setName(pi.name);
        pr.setManager(theOneThatCreates.getId());
        pr.addUser(theOneThatCreates);
        return pr;
    }
}
