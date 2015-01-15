package org.freelo.model.projects;

import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.users.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by piotr on 2014-12-09.
 */
public class ProjectMembersManagement {

    public static User getUser(String email){
        Session session = HibernateSessionFactoryBean.getSession();
        User user = null;
        try{
            session.beginTransaction();

            @SuppressWarnings("unchecked")
			List<User> users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
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


    public static Project getProject(int id){
        Session session = HibernateSessionFactoryBean.getSession();
        Project project = null;
        try {
            project = (Project) session.get(Project.class, id);
        }
        catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
        return project;
    }

}
