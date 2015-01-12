package org.freelo.controller.tasks;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import org.freelo.view.tasks.TaskPage;

import javax.management.Notification;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Adrian on 12-01-2015.
 */
public class TaskPageController {
    TaskPage tp;

    public TaskPageController(TaskPage tp){
        this.tp = tp;
        tp.shoutButton.addClickListener(new AddShoutLine());
    }


    class AddShoutLine implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent event) {
            addShout(tp.shoutField.getValue(), tp.userName);
            tp.shoutField.setValue("");
        }

    }

    public void addShout(String shout, String userName){

        String s = tp.shoutBoxArea.getValue();
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm:ssa");
        tp.shoutBoxArea.setValue(s + "[" + ft.format(date) + "]" + " " + userName + ": " + shout + "\n");
    }

    public void updateShoutFromDB(){

        String actualText = tp.shoutBoxArea.getValue();
        //shout = get.from.database;
        //tp.shoutBoxArea.setValue(actualText + shout);
    }

}
