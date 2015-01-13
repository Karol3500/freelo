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
    public Button createButton;
    public ProjectItem pi;
    CreateProjectSubwindowController c;

    public Subwindow(final VerticalLayout container2) {
        super("New project");
        c = new CreateProjectSubwindowController(this);
        center();
        HorizontalLayout main = new HorizontalLayout();
        main.addStyleName("projectpopup");
        main.setSizeFull();
        setContent(main);
        setHeight("200px");
        setWidth("300px");
        setPositionY(50);
        setPositionX(50);

        final TextField ProjectName = new TextField("Enter project name");
        ProjectName.focus();
        // todo.add date
        createButton = new Button("Create", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;
            @Override
            public void buttonClick(Button.ClickEvent event) {
                pi = createProject(ProjectName, container2);
                close();
                c.createProject(pi);
            }
        });
        createButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        VerticalLayout itemplacement = new VerticalLayout();
        itemplacement.setSizeFull();
        itemplacement.addComponent(ProjectName);
        itemplacement.addComponent(createButton);
        main.addComponent(itemplacement);

    }



    public ProjectItem createProject(TextField projectName, VerticalLayout container2) {
        final String name = projectName.getValue();
        String manager = (String) VaadinSession.getCurrent().getAttribute("user");
        ProjectItem pi = new ProjectItem(name, manager);
        container2.addComponent(pi);
        return pi;
    }


}
