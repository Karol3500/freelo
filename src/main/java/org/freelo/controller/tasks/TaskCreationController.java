package org.freelo.controller.tasks;

import com.vaadin.ui.Button;
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
            window.close();
        }
    }
}
