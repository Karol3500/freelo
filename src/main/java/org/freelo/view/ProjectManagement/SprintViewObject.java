package org.freelo.view.ProjectManagement;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.freelo.view.tasks.TaskPage;

/**
 * Created by Konrad on 2015-01-13.
 */
public class SprintViewObject extends TaskPage implements View{
    public final Button SprintButton = new Button("Sprint", new Button.ClickListener() {
        private static final long serialVersionUID = 2181474159879122119L;
        @Override
        public void buttonClick(Button.ClickEvent event) {
            getUI().getNavigator().navigateTo(TaskPage.NAME);
        }
    });
}
