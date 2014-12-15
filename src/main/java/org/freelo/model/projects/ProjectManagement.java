package org.freelo.model.projects;

import org.freelo.model.HibernateSessionFactoryBean;

import org.hibernate.Session;


import java.util.List;

/**
 * Created by piotr on 2014-12-15.
 **/
public class ProjectManagement {

    public static List<Project> getDoneTask (int userId) {
        Session session = HibernateSessionFactoryBean.getSession();
        List<Project> DoneTask = null;

        try {
            DoneTask = session.createQuery(
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
        return DoneTask;

    }

    public static List<Project> getToDoTask (int userId) {
        Session session = HibernateSessionFactoryBean.getSession();
        List<Project> ToDoTask = null;

        try {
            ToDoTask = session.createQuery(
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
        return ToDoTask;

    }

    public static List<Project> getOnGoingTask (int userId) {
        Session session = HibernateSessionFactoryBean.getSession();
        List<Project> OnGoingTask = null;

        try {
            OnGoingTask = session.createQuery(
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
        return OnGoingTask;

    }

}
