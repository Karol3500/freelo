package org.freelo.model.projects;

import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.users.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 2014-12-15.
 **/
@SuppressWarnings("unchecked")
public class ProjectManagement {

    // returns project ID if found, null if user project exist
    public static Integer getProjectID(Integer managerID, String projectName){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer projectID = null;
        try{
            session.beginTransaction();

            List<Project> projects = session.createQuery("FROM Project P WHERE P.manager = "+managerID+" AND P.name = '"+projectName+"'").list();
            if (!projects.isEmpty())
                projectID = projects.get(0).getId();

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return projectID;
    }

    // returns project  if found, null if project doesnt exist
    public static Project getProject(Integer managerID, String projectName){
        Session session = HibernateSessionFactoryBean.getSession();
        Project project = null;
        try{
            session.beginTransaction();

            List<Project> projects = session.createQuery("FROM Project P WHERE P.manager = "+managerID+" AND P.name = '"+projectName+"'").list();
            if (!projects.isEmpty()) {
                project = (Project) projects.get(0);
                project.setSprints(project.getSprints());
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return project;
    }

    // returns project handler if found, null if project doesnt exist
    public static Project getProject(Integer ID){
        Session session = HibernateSessionFactoryBean.getSession();
        Project project = null;
        try{
            session.beginTransaction();

			List<Project> projects = session.createQuery("FROM Project P WHERE P.id = "+ID).list();
            if (!projects.isEmpty()){
                project = (Project) projects.get(0);
            project.setSprints(project.getSprints());
        }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return project;
    }

    // returns project handler if found, null if project doesnt exist
    public static List<Project> getProjects(User user){
        Session session = HibernateSessionFactoryBean.getSession();
        List<Project> returnProjects = new ArrayList<Project>();
        try{
            session.beginTransaction();

            List<Project> projects = session.createQuery("FROM Project P WHERE 1 = "+1).list();
            if (!projects.isEmpty()){
                for(Project p : projects){
                    if(p.getUsers().contains(user))
                        returnProjects.add(p);
                }
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return returnProjects;
    }

    // add a new project, return project ID, null if adding project failed (if the manager have the project with same name)
    public static Integer addProject(Project project){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer projectID = null;
        try{
            session.beginTransaction();

            List<Project> projects = session.createQuery("FROM Project P WHERE P.manager = "+project.getManager()+" AND P.name = '"+project.getName()+"'").list();
            if (projects.isEmpty()) {
                projectID = (Integer) session.save(project);
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return projectID;
    }

    public static Integer updateProject(Project project){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer projectID = null;
        try{
            session.beginTransaction();

            List<Project> projects = session.createQuery("FROM Project P WHERE P.manager = "+project.getManager()+" AND P.name = '"+project.getName()+"'").list();
            if (!projects.isEmpty()) {
                Project p = (Project)projects.get(0);
                p.setManager(project.getManager());
                p.setUsers(project.getUsers());
                p.setSprints(project.getSprints());
                p.setName(project.getName());
                session.update(p);
            }
            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return projectID;
    }

    // remove project from DB
    public static void deleteProject(Integer managerID, String projectName){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();

            List<Project> projects = session.createQuery("FROM Project P WHERE P.manager = "+managerID+" AND P.name = '"+projectName+"'").list();
            if (!projects.isEmpty()) {
                Project project = (Project)session.get(Project.class, ((Project) projects.get(0)).getId());
                session.delete(project);
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    // remove project from DB
    public static void deleteProject(Integer projectID){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();

            Project project = (Project)session.get(Project.class, projectID);
            session.delete(project);

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
}
