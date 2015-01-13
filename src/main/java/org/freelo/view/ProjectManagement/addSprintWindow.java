package org.freelo.view.ProjectManagement;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;

/**
 * Created by Konrad on 2015-01-13.
 */
public class addSprintWindow extends Window {
    private static final long serialVersionUID = 5683290459141040269L;
    //        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //public Button createButton;
    //public ProjectItem pi;
    //CreateProjectSubwindowController c;
    public String sprint_name;
    public addSprintWindow(final VerticalLayout nextcontainer) {
        super("New sprint");
        //c = new CreateProjectSubwindowController(this);
        center();
        HorizontalLayout main = new HorizontalLayout();
        main.addStyleName("projectpopup");
        main.setSizeFull();
        setContent(main);
        setHeight("300px");
        setWidth("300px");
        setPositionY(50);
        setPositionX(50);

        final TextField ProjectName = new TextField("Enter sprint name");
        ProjectName.focus();
        // todo.add date
        final PopupDateField startDatePicker = new PopupDateField("Start date");
        final PopupDateField endDatePicker = new PopupDateField("End date");

        Button createButton = new Button("Create", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                //pi = createProject(ProjectName, container2);

                //c.createProject(pi);

                SprintViewObject sprint = new SprintViewObject(ProjectName.getValue(),
                        startDatePicker.getValue(), endDatePicker.getValue());
                nextcontainer.addComponent(sprint.button);
                close();
            }
        });
        createButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        VerticalLayout itemplacement = new VerticalLayout();
        itemplacement.setSizeFull();
        itemplacement.addComponent(ProjectName);
        itemplacement.addComponent(startDatePicker);
        itemplacement.addComponent(endDatePicker);
        itemplacement.addComponent(createButton);
        main.addComponent(itemplacement);

    }

}

