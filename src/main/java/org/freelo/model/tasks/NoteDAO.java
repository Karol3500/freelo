package org.freelo.model.tasks;

import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.users.UserManagement;
import org.freelo.view.tasks.TaskCard;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;

/**
 * Created by karol on 30.11.14.
 */
@SuppressWarnings("unchecked")
@org.springframework.stereotype.Component
@Scope("singleton")
public class NoteDAO {

    public static void saveNote(Note note){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.getTransaction().begin();
            Integer id = (Integer)session.save(note);
            session.getTransaction().commit();
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
    }

    public static void update(Note n) {
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();
            session.saveOrUpdate(n);
            session.getTransaction().commit();
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

