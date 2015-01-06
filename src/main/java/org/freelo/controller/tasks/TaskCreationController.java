package org.freelo.controller.tasks;

import com.vaadin.ui.Button;
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
        @Override
        public void buttonClick(Button.ClickEvent event) {
            TaskCard tc = window.createTask();
            System.out.println(tc.getTaskNote());
            tc.setDbId(persist(tc));
            Note n = NoteDAO.getNote(tc.getDbId());
            n.setText("From db: " + n.getText());
            createTask(n);
            window.close();
        }
    }

    public void createTask(Note n){
        TaskCard tc = new TaskCard(n.getTaskName(), n.getPriority(), n.getText());
        window.createTask(tc);
    }

    private Integer persist(TaskCard tc){
        return NoteDAO.saveTaskCard(tc);
    }
}
