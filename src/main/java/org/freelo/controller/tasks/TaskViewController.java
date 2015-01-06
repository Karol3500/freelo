package org.freelo.controller.tasks;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import org.freelo.model.tasks.Note;
import org.freelo.model.tasks.NoteDAO;
import org.freelo.view.tasks.TaskCard;
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
            removeFromDb(window.tc);
            window.deleteTask();
            window.close();
        }
    }

    private void removeFromDb(TaskCard tc) {
        if(tc.getDbId()==null)
            return;
        Note n = NoteDAO.getNote(tc.getDbId());
        if(n == null)
            return;
        NoteDAO.deleteNoteById(n.getId());
    }
}
