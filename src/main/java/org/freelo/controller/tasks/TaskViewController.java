package org.freelo.controller.tasks;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import org.freelo.view.tasks.TaskViewWindow;

/**
 * Created by karol on 16.12.14.
 */
public class TaskViewController {
    TaskViewWindow window;
    public TaskViewController(TaskViewWindow window){
        this.window = window;
        window.deleteTaskButton.addClickListener(new deleteButtonOnClickListener());
    }

    class deleteButtonOnClickListener implements ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent event) {
            window.deleteTask();
        }
    }
}
