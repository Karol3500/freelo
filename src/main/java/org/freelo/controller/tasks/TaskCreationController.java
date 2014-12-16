package org.freelo.controller.tasks;

import com.vaadin.ui.Button;
import org.freelo.model.tasks.*;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
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
            window.createTask();
            //persist(window.createTask());
            window.close();
        }
    }

    private void persist(TaskCard tc){
        Note n = new Note();
        User u = UserManagement.getUser(tc.creator);
        n.setUser(u);
        n.setTc(tc);
        NoteDAO.saveNote(n);
    }
}
