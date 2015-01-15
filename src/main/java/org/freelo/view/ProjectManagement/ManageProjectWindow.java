package org.freelo.view.ProjectManagement;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Created by Konrad on 2015-01-13.
 */
public class ManageProjectWindow extends Window {
    private static final long serialVersionUID = 5683290459141040269L;
    //        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //public Button createButton;
    //public ProjectItem pi;
    //CreateProjectSubwindowController c;

    public ManageProjectWindow() {
        super("Project");
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


        Button updateButton = new Button("Update", new Button.ClickListener() {
            private static final long serialVersionUID = 2181474159749122119L;

            @Override
            public void buttonClick(Button.ClickEvent event) {

                close();


            }
        });
        updateButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        VerticalLayout itemplacement = new VerticalLayout();
        itemplacement.setSizeFull();
        itemplacement.addComponent(updateButton);
        main.addComponent(itemplacement);

    }

}
