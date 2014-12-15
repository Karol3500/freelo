package org.freelo.model.projects;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;

/**
 * Created by piotr on 2014-12-15.
 */
public class ProjectPrivilegesManagement {
    public static Integer addProjectPrivileges(ProjectPrivileges newProjectPrivileges) {
        Session session = HibernateSessionFactoryBean.getSession();
        Integer ID = null;

        try {
            session.beginTransaction();

            List privileges = session.createQuery("FROM ProjectPrivileges P WHERE P.ID = '" + newProjectPrivileges.getID() + "'").list();
            if (privileges.isEmpty())
                ID = (Integer) session.save(newProjectPrivileges);
            session.getTransaction().commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally {
            session.close();
        }
        return ID;
    }


    public static boolean deleteProjectPrivileges(ProjectPrivileges oldProjectPrivileges) {
        Session session = HibernateSessionFactoryBean.getSession();
        boolean successfulDeletion = false;

        try {
            session.beginTransaction();

            List privileges = session.createQuery("FROM ProjectPrivileges P WHERE P.ID = '"+ oldProjectPrivileges.getID() +"'").list();
            if (!privileges.isEmpty()) {
                ProjectPrivileges temp = (ProjectPrivileges)session.get(ProjectPrivileges.class, oldProjectPrivileges.getID());
                session.delete(temp);
                successfulDeletion = true;
            }
            session.getTransaction().commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally {
            session.close();
        }
        return successfulDeletion;
    }


    public static List<ProjectPrivileges> listPrivileges() {
        Session session = HibernateSessionFactoryBean.getSession();
        List<ProjectPrivileges> privileges = null;

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM ProjectPrivileges");
            privileges = (List<ProjectPrivileges>) query.list();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally {
            session.close();
        }
        return privileges;
    }
}
