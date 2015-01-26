package org.freelo.controller.projects;

import com.vaadin.ui.Button;
import org.freelo.model.projects.Project;
import org.freelo.model.projects.ProjectManagement;
import org.freelo.model.projects.SprintDAO;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.UserManagement;

/**
 * Created by karol on 13.01.15.
 */
public class SprintCreationListener implements Button.ClickListener {
	private static final long serialVersionUID = 1L;
	Project project;
    public SprintCreationListener(Project pr){
        project = pr;
    }
    @Override
    public void buttonClick(Button.ClickEvent event) {
        persistSprint();
    }

    private void persistSprint() {
        Project p = ProjectManagement.getProject(project.getId());
        Sprint s = new Sprint();
        s.setLeader(UserManagement.getUser(p.getManager()).getEmail());
        p.addSprint(s);
        SprintDAO.saveOrUpdateSprint(s);
    }
}
