package org.freelo.model.users;

import java.util.ArrayList;
import java.util.List;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Created by Artur on 12/8/2014.
 */
@SuppressWarnings("unchecked")
public class GroupManagement {
    // returns group ID if successful, returns null if error occurred (e.g. group already exists)
    public static Integer addGroup(Group newGroup) {
        Session session = HibernateSessionFactoryBean.getSession();
        Integer ID = null;

        try {
            session.beginTransaction();

            List<Group> groups = session.createQuery("FROM Group G WHERE G.ID = '" + newGroup.getID() + "'").list();
            if (groups.isEmpty())
                ID = (Integer) session.save(newGroup);
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

    // returns true if successful, or false if some error occurred
    public static boolean deleteGroup(Privilege oldGroup) {
        Session session = HibernateSessionFactoryBean.getSession();
        boolean successfulDeletion = false;

        try {
            session.beginTransaction();

            List<Group> groups = session.createQuery("FROM Group G WHERE G.ID = '"+ oldGroup.getID() +"'").list();
            if (!groups.isEmpty()) {
                Group temp = (Group)session.get(Group.class, oldGroup.getID());
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
    public static List<Group> listGroups() {
        Session session = HibernateSessionFactoryBean.getSession();
        List<Group> groups = null;

        try {
            session.beginTransaction();
			List<Group> query = (List<Group>) session.createQuery("FROM Group").list();
            groups = new ArrayList<Group>(query);

            session.getTransaction().commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally {
            session.close();
        }
        return groups;
    }
}
