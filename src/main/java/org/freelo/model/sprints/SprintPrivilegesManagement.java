package org.freelo.model.sprints;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;

/**
 * Created by piotr on 2014-12-15.
 */
public class SprintPrivilegesManagement {
    public static Integer addSprintPrivileges(SprintPrivileges newSprintPrivileges) {
        Session session = HibernateSessionFactoryBean.getSession();
        Integer ID = null;

        try {
            session.beginTransaction();

            List privileges = session.createQuery("FROM SprintPrivileges P WHERE P.ID = '" + newSprintPrivileges.getID() + "'").list();
            if (privileges.isEmpty())
                ID = (Integer) session.save(newSprintPrivileges);
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


    public static boolean deleteSprintPrivileges(SprintPrivileges oldSprintPrivileges) {
        Session session = HibernateSessionFactoryBean.getSession();
        boolean successfulDeletion = false;

        try {
            session.beginTransaction();

            List privileges = session.createQuery("FROM SprintPrivileges P WHERE P.ID = '"+ oldSprintPrivileges.getID() +"'").list();
            if (!privileges.isEmpty()) {
                SprintPrivileges temp = (SprintPrivileges)session.get(SprintPrivileges.class, oldSprintPrivileges.getID());
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

    public static List<SprintPrivileges> listPrivileges() {
        Session session = HibernateSessionFactoryBean.getSession();
        List<SprintPrivileges> privileges = null;

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM SprintPrivileges");
            privileges = (List<SprintPrivileges>) query.list();
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
