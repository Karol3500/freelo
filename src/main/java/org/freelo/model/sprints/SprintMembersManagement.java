package org.freelo.model.sprints;

import org.freelo.model.HibernateSessionFactoryBean;

import org.freelo.model.users.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import java.util.List;

/**
 * Created by piotr on 2014-12-09.
 */
public class SprintMembersManagement {

    public static Integer addMember(SprintMembers newMembers) {
        Session session = HibernateSessionFactoryBean.getSession();
        Integer ID = null;

        try {
            session.beginTransaction();

            List members = session.createQuery("FROM SprintMembers S WHERE S.id = " + newMembers.getId()).list();
            if (members.isEmpty())
                ID = (Integer) session.save(newMembers);
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

            public static boolean deleteMember(SprintMembers oldMembers) {
        Session session = HibernateSessionFactoryBean.getSession();
        boolean successfulDeletion = false;

        try {
            session.beginTransaction();

            List members = session.createQuery("FROM SprintMembers P WHERE P.id = '"+ oldMembers.getId() +"'").list();
            if (!members.isEmpty()) {
                SprintMembers temp = (SprintMembers)session.get(SprintMembers.class, oldMembers.getId());
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




    public static User getUser(String email){
        Session session = HibernateSessionFactoryBean.getSession();
        User user = null;
        try{
            session.beginTransaction();

            List users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
            if (!users.isEmpty())
                user = (User) users.get(0);

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return user;
    }

    public static Sprint getSprint(int id){
        Session session = HibernateSessionFactoryBean.getSession();
        Sprint sprint = null;
        try {
            sprint = (Sprint) session.get(Sprint.class, id);
        }
        catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
        return sprint;
    }

}
