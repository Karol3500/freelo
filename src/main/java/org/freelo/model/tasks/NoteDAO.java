package org.freelo.model.tasks;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.freelo.model.HibernateSessionFactoryBean;
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

    public static Note getNoteByTaskCard(TaskCard tc){
        Session session = HibernateSessionFactoryBean.getSession();
        List<Note> notes = null;
        Note n = null;
        try {
            notes = session.createQuery(
                    "FROM Note WHERE Note.tc = :taskCard")
                    .setParameter("taskCard", tc)
                    .list();
            if(notes != null){
                n = notes.get(0);
            }
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

    public static boolean saveNote(Note note){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.save(note);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
        finally{
            session.close();
        }
    }

    public static List<Note> getNotesBelongingToUser(int userId){
        Session session = HibernateSessionFactoryBean.getSession();
        List<Note> notes = null;
        try {
            notes = session.createQuery(
                    "FROM Note WHERE User.id = :userId")
                    .setParameter("userId", userId)
                    .list();
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
}

