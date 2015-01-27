package org.freelo.controller.tasks;

import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.projects.SprintDAO;
import org.freelo.model.sprints.Sprint;
import org.freelo.model.tasks.Note;
import org.freelo.view.tasks.TaskCard;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import org.hibernate.Session;

/**
 * Created by karol on 15.12.14.
 */

public class TaskController {
    TaskCard tc;
    Sprint sprint;

    public TaskController(TaskCard tc,Sprint sprint){
        this.sprint = sprint;
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

            Note n = TaskCreationController.createNoteFromTaskCard(tc);
            Session session = HibernateSessionFactoryBean.getSession();
            try {
                session.getTransaction().begin();
                sprint = SprintDAO.getSprint(sprint.getId());
                if (currentContainerIndex == 0) {
                    sprint.getToDo().remove(n);
                    sprint.addNoteOngoing(n);
                }
                if (currentContainerIndex == 1) {
                    sprint.getOnGoing().remove(n);
                    sprint.addNoteDone(n);
                }
                session.merge(sprint);
                session.getTransaction().commit();
                current.removeComponent(tc);
                VerticalLayout newContainer = tc.columns.get(currentContainerIndex + 1);
                newContainer.addComponent(tc);
                tc.currentContainer = newContainer;
            }
            catch(Exception ex) {}
            finally{
                session.close();
            }
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

            Note n = TaskCreationController.createNoteFromTaskCard(tc);
            Session session = HibernateSessionFactoryBean.getSession();
            try {
                session.getTransaction().begin();
                sprint = SprintDAO.getSprint(sprint.getId());
                if (currentContainerIndex == 1) {
                    sprint.getOnGoing().remove(n);
                    sprint.addNoteToDo(n);
                }
                if (currentContainerIndex == 2) {
                    sprint.getDone().remove(n);
                    sprint.addNoteOngoing(n);
                }
                session.merge(sprint);
                session.getTransaction().commit();
                current.removeComponent(tc);
                VerticalLayout newContainer = tc.columns.get(currentContainerIndex-1);
                newContainer.addComponent(tc);
                tc.currentContainer = newContainer;
            }
            catch(Exception ex) {}
            finally{
                session.close();
            }
        }
    }
}
