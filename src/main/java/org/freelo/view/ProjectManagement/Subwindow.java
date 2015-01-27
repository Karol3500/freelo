package org.freelo.view.ProjectManagement;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.freelo.controller.projects.CreateProjectSubwindowController;

/**
 * Created by Konrad on 2015-01-13.
 */
public class Subwindow extends Window {
    private static final long serialVersionUID = 5678234591401040269L;
    CreateProjectSubwindowController contoller;

    public Subwindow(final VerticalLayout container2) {
        super("New project");
        contoller = new CreateProjectSubwindowController(this);
        VerticalLayout container = container2;
        center();
        HorizontalLayout main = new HorizontalLayout();
        main.addStyleName("projectpopup");
        main.setSizeFull();
        setContent(main);
        setHeight("200px");
        setWidth("300px");
        setPositionY(50);
        setPositionX(50);

        final TextField projectName = new TextField("Enter project name");
        projectName.focus();
        // todo.add date
        Button createButton = new Button("Create");
        createButton.addClickListener(new CreateProjectButtonClickListener(projectName,container2));
        createButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        VerticalLayout itemplacement = new VerticalLayout();
        itemplacement.setSizeFull();
        itemplacement.addComponent(projectName);
        itemplacement.addComponent(createButton);
        main.addComponent(itemplacement);
    }
    class CreateProjectButtonClickListener implements Button.ClickListener {
        private static final long serialVersionUID = 2181474159749122119L;
        TextField projectName;
        VerticalLayout container;

        public CreateProjectButtonClickListener(TextField projectName, VerticalLayout container){
            this.projectName = projectName;
            this.container = container;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            contoller.createProject(createProjectItem(projectName, container));
            close();
        }
    }

    public ProjectItem createProjectItem(TextField projectName, VerticalLayout container2) {
        final String name = projectName.getValue();
        String manager = (String) VaadinSession.getCurrent().getAttribute("user");
        ProjectItem pi = new ProjectItem(name, manager);
        container2.addComponent(pi);
        return pi;
    }
}
