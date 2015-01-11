package org.freelo.model.sprints;

import org.freelo.model.HibernateSessionFactoryBean;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by piotr on 2014-12-15.
 **/
public class SprintManagement {


    public static Integer getSprint(String sprintName){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer sprint = null;
        try{
            session.beginTransaction();
            List projects = session.createQuery("FROM Sprint S WHERE S.sprintName = '"+sprintName+"'").list();
            if (!projects.isEmpty())
                sprint = ((Sprint) projects.get(0)).getId();
            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return sprint;
    }

    public static Integer addSprint(Sprint sprintName){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer sprintID = null;
        try{
            session.beginTransaction();
            List projects = session.createQuery("FROM Sprint S WHERE S.sprintName  = '"+sprintName+"'").list();
            if (projects.isEmpty()) {
                sprintID = (Integer) session.save(sprintName);
            }
            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return sprintID;
    }



    public static void deleteSprint(String sprintName){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();
            List projects = session.createQuery("FROM Sprint S WHERE S.sprintName = '"+sprintName+"'").list();
            if (!projects.isEmpty()) {
                Sprint sprint = (Sprint)session.get(Sprint.class, ((Sprint) projects.get(0)).getId());
                session.delete(sprint);
            }
            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }


    public static void updateSprint(Sprint sprint){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();
            Sprint tempSprint = (Sprint)session.get(Sprint.class, sprint.getId());
            if (tempSprint != null) {
                tempSprint.setStartDate(sprint.getStartDate());
                tempSprint.setEndDate(sprint.getEndDate());
                tempSprint.setLeader(sprint.getLeader());
                tempSprint.setSprint(sprint.getSprint());
                session.update(tempSprint);
            }
            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }


}
