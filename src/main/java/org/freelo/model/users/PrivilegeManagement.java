package org.freelo.model.users;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;

/**
 * Created by Artur on 12/8/2014.
 */

public class PrivilegeManagement {

    // returns privilege ID if successful, returns null if error occurred (e.g. privilege already exists)
    public static Integer addPrivilege(String description) {
        Session session = HibernateSessionFactoryBean.getSession();
        Integer privilegeID = null;

        try {
            session.beginTransaction();

            List privileges = session.createQuery("FROM Privilege P WHERE P.description = '" + description + "'").list();
            if (privileges.isEmpty()) {
                Privilege privilege = new Privilege();
                privilege.setDescription(description);
                privilegeID = (Integer) session.save(privilege);
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
        return privilegeID;
    }

    // returns true if successful, or false if some error occurred
    public static boolean deletePrivilege(Privilege oldPrivilege) {
        Session session = HibernateSessionFactoryBean.getSession();
        boolean successfulDeletion = false;

        try {
            session.beginTransaction();

            List privileges = session.createQuery("FROM Privilege P WHERE P.ID = '"+ oldPrivilege.getID() +"'").list();
            if (!privileges.isEmpty()) {
                Privilege temp = (Privilege)session.get(Privilege.class, oldPrivilege.getID());
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

    // returns a list of all the privileges that exist in the database
    public static List<Privilege> listPrivileges() {
        Session session = HibernateSessionFactoryBean.getSession();
        List<Privilege> privileges = null;

        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM Privilege");
            privileges = (List<Privilege>) query.list();

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
