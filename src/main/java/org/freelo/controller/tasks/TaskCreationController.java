package org.freelo.controller.tasks;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.freelo.model.projects.SprintDAO;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.tasks.*;
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
                deleteTaskCardFromViewAndSubstituteItWithVersionTakenFromDb(tc);
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

    private void deleteTaskCardFromViewAndSubstituteItWithVersionTakenFromDb(TaskCard tc) {
        deleteTaskFromView(tc);
        Note n = NoteDAO.getNote(tc.getDbId());
        createTask(n);
    }

    private TaskCard createAndPersistTaskAfterCreateButtonClicked() {
        TaskCard tc = window.createTask();
        persist(tc);
        window.close();
        return tc;
    }


    public void createTask(Note n){
        TaskCard tc = new TaskCard(n.getTaskName(), n.getPriority(), n.getText());
        window.createTask(tc,0);
    }

    void deleteTaskFromView(TaskCard tc){
        tc.taskList.remove(tc);
        tc.currentContainer.removeComponent(tc);
    }

    private void persist(TaskCard tc){
        sprint = SprintDAO.getSprint(sprint.getId());
        tc.setDbId(NoteDAO.saveTaskCard(tc));
        Note n = NoteDAO.getNote(tc.getDbId());
        sprint.addNoteToDo(n);
        SprintDAO.saveOrUpdateSprint(sprint);
    }
}
