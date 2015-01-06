package org.freelo.model.tasks;

import java.util.List;

import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.users.UserManagement;
import org.freelo.view.tasks.TaskCard;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;

/**
 * Created by karol on 30.11.14.
 */
@org.springframework.stereotype.Component
@Scope("singleton")
public class NoteDAO {

    public static Note getNote(int id){
        Session session = HibernateSessionFactoryBean.getSession();
        Note n = null;
        try{
            session.beginTransaction();
            n = (Note)session.get(Note.class,id);
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
        return n;
    }

    public static Integer saveNote(Note note){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.getTransaction().begin();
            Integer id = (Integer)session.save(note);
            session.getTransaction().commit();
            return id;
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
        finally{
            session.close();
        }
    }

    public static List<Note> getNotesBelongingToUser(String userName){
        Session session = HibernateSessionFactoryBean.getSession();
        List notes = null;
        try {
            notes = session.createQuery("FROM Note N WHERE N.user= '"+userName+"'").list();
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
        return notes;
    }

    public static void deleteNoteById(int id){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();
            session.delete("Note",getNote(id));
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
    }

    public static Integer saveTaskCard(TaskCard tc) {
        Note n = new Note();
        n.setPriority(tc.priorityString);
        n.setTaskName(tc.getTaskName());
        n.setText(tc.taskNote);
        n.setUser(UserManagement.getUser(tc.getUser()));
        return saveNote(n);
    }
}

