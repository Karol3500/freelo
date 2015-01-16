package org.freelo.controller.tasks;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.freelo.model.tasks.*;
import org.freelo.view.tasks.TaskCard;
import org.freelo.view.tasks.TaskCreationWindow;


/**
 * Created by karol on 16.12.14.
 */
public class TaskCreationController {
    TaskCreationWindow window;

    public TaskCreationController(TaskCreationWindow window){
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
        tc.setDbId(persist(tc));
        window.close();
        return tc;
    }


    public void createTask(Note n){
        TaskCard tc = new TaskCard(n.getTaskName(), n.getPriority(), n.getText());
        window.createTask(tc);
    }

    void deleteTaskFromView(TaskCard tc){
        tc.taskList.remove(tc);
        tc.currentContainer.removeComponent(tc);
    }

    private Integer persist(TaskCard tc){
        return NoteDAO.saveTaskCard(tc);
    }
}
