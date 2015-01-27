package org.freelo.model.projects;

import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.sprints.Sprint;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by karol on 26.01.15.
 */
public class SprintDAO {
    public static Integer saveOrUpdateSprint(Sprint sprint) {
        Session session = HibernateSessionFactoryBean.getSession();
        Integer sprintId = null;
        try{
            session.beginTransaction();

            session.saveOrUpdate(sprint);

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return sprintId;
    }

    public static Sprint getSprint(int id){
        Session session = HibernateSessionFactoryBean.getSession();
        Sprint s = null;
        try{
            session.beginTransaction();
            s = (Sprint)session.get(Sprint.class,id);
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
        return s;
    }
}
