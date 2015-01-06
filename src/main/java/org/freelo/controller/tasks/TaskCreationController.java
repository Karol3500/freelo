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
            tc.setDbId(persist(tc));
            System.out.println("chuj dupa pizda kurwa szmata" +tc.getDbId());
            Note n = NoteDAO.getNote(tc.getDbId());
            System.out.println("\n\n\n\n"+n.getId());
            window.close();
        }
    }

    private Integer persist(TaskCard tc){
        return NoteDAO.saveTaskCard(tc);
    }
}
