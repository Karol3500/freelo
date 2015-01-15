package org.freelo.controller.tasks;

import org.freelo.view.tasks.TaskCard;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by karol on 15.12.14.
 */

public class TaskController {
    TaskCard tc;
    public TaskController(TaskCard tc){
        this.tc = tc;
        tc.rightButton.addClickListener(new RightMover());
        tc.leftButton.addClickListener(new LeftMover());
    }

    class RightMover implements Button.ClickListener{
		private static final long serialVersionUID = 1L;

		@Override
        public void buttonClick(Button.ClickEvent event) {
            VerticalLayout current = tc.currentContainer;
            int currentContainerIndex = tc.columns.indexOf(current);
            if(currentContainerIndex==2)
                return;
            current.removeComponent(tc);
            VerticalLayout newContainer = tc.columns.get(currentContainerIndex+1);
            newContainer.addComponent(tc);
            tc.currentContainer = newContainer;
        }
    }

    class LeftMover implements Button.ClickListener{
		private static final long serialVersionUID = 1L;

		@Override
        public void buttonClick(Button.ClickEvent event) {
            VerticalLayout current = tc.currentContainer;
            int currentContainerIndex = tc.columns.indexOf(current);
            if(currentContainerIndex==0)
                return;
            current.removeComponent(tc);
            VerticalLayout newContainer = tc.columns.get(currentContainerIndex-1);
            newContainer.addComponent(tc);
            tc.currentContainer = newContainer;
        }
    }


}
