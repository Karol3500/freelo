package org.freelo.controller.tasks;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import org.freelo.model.projects.SprintDAO;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.tasks.Note;
import org.freelo.model.tasks.NoteDAO;
import org.freelo.model.users.UserManagement;
import org.freelo.view.tasks.TaskCard;
import org.freelo.view.tasks.TaskViewWindow;

import java.util.List;

/**
 * Created by karol on 16.12.14.
 */
public class TaskViewController {
    TaskViewWindow window;
    Sprint s;
    public TaskViewController(TaskViewWindow window, Sprint sprint){
        this.s = sprint;
        this.window = window;
        window.deleteTaskButton.addClickListener(new deleteButtonOnClickListener());
    }

    class deleteButtonOnClickListener implements ClickListener{
		private static final long serialVersionUID = 1L;

		@Override
        public void buttonClick(Button.ClickEvent event) {
            removeFromDb(window.tc);
            window.deleteTask();
            window.close();
        }
    }

    void removeFromDb(TaskCard tc) {
        s=SprintDAO.getSprint(s.getId());
        Note n = new Note();
        n.setPriority(tc.priorityString);
        n.setTaskName(tc.getTaskName());
        n.setText(tc.taskNote);
        n.setUser(UserManagement.getUser(tc.getUser()));
        List<Note> notes = null;
        int columnId = tc.columns.indexOf(tc.currentContainer);
        if(columnId==0){
            notes = s.getToDo();
        }
        else if(columnId==1){
            notes = s.getOnGoing();
        }
        if(columnId==0){
            notes = s.getDone();
        }
        notes.remove(n);
        SprintDAO.saveOrUpdateSprint(s);
    }
}
