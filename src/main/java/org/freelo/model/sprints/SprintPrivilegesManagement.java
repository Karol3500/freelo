package org.freelo.model.sprints;

import java.util.List;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Created by piotr on 2014-12-15.
 */
public class SprintPrivilegesManagement {
    public static Integer addSprintPrivileges(SprintPrivileges newSprintPrivileges) {
        Session session = HibernateSessionFactoryBean.getSession();
        Integer ID = null;

        try {
            session.beginTransaction();

			//List<SprintPrivileges> privileges = session.createQuery("FROM SprintPrivileges P WHERE P.description = '" + newSprintPrivileges.getDescription() + "'").list();
            //if (privileges.isEmpty())
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

            @SuppressWarnings("unchecked")
			List<SprintPrivileges> privileges = session.createQuery("FROM SprintPrivileges P WHERE P.ID = '"+ oldSprintPrivileges.getID() +"'").list();
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

    @SuppressWarnings("unchecked")
	public static List<SprintPrivileges> listPrivileges() {
        Session session = HibernateSessionFactoryBean.getSession();
        List<SprintPrivileges> privileges = null;

        try {
            session.beginTransaction();
            privileges = session.createQuery("FROM SprintPrivileges").list();
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
