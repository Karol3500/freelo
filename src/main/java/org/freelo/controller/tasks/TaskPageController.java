package org.freelo.controller.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.ui.UI;
import org.freelo.view.tasks.EventSubwindow;
import org.freelo.view.tasks.TaskPage;

import com.vaadin.ui.Button;

/**
 * Created by Adrian on 12-01-2015.
 */
public class TaskPageController {
    TaskPage tp;

    public TaskPageController(TaskPage tp){
        this.tp = tp;
        tp.shoutButton.addClickListener(new AddShoutLine());
        tp.createEventButton.addClickListener(new openSubwindow());
    }

    class openSubwindow implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent event) {
            EventSubwindow subWind = new EventSubwindow(tp);
            UI.getCurrent().addWindow(subWind);
        }

    }

    class AddShoutLine implements Button.ClickListener{
		private static final long serialVersionUID = 1L;

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



}
