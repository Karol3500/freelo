package org.freelo.controller.tasks;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.freelo.model.projects.SprintDAO;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.tasks.*;
import org.freelo.model.users.UserManagement;
import org.freelo.view.tasks.TaskCard;
import org.freelo.view.tasks.TaskCreationWindow;


/**
 * Created by karol on 16.12.14.
 */
public class TaskCreationController {
    TaskCreationWindow window;
    Sprint sprint;

    public TaskCreationController(TaskCreationWindow window, Sprint s){
        this.sprint = s;
        this.window = window;
        this.window.createTaskButton.addClickListener(new CreateTaskButtonOnClickListener());
    }

    class CreateTaskButtonOnClickListener implements Button.ClickListener{
		private static final long serialVersionUID = 1L;

		@Override
        public void buttonClick(Button.ClickEvent event) {
            try {
                TaskCard tc = createAndPersistTaskAfterCreateButtonClicked();
            }
            catch(Exception ex){
                ex.printStackTrace();
                new Notification("An error occured while saving task card to database."
                        +"Please try again another time.");
                if(window != null){
                    window.close();
                }
            }
        }
    }

    private TaskCard createAndPersistTaskAfterCreateButtonClicked() {
        TaskCard tc = window.createTask(sprint);
        persist(tc);
        window.close();
        return tc;
    }

    private void persist(TaskCard tc){
        sprint = SprintDAO.getSprint(sprint.getId());
        Note n = new Note();
        n.setPriority(tc.priorityString);
        n.setTaskName(tc.getTaskName());
        n.setText(tc.taskNote);
        n.setUser(UserManagement.getUser(tc.getUser()));
        sprint.addNoteToDo(n);
        SprintDAO.saveOrUpdateSprint(sprint);
    }
}
