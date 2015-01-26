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
    public static Integer saveSprint(Sprint sprint) {
        Session session = HibernateSessionFactoryBean.getSession();
        Integer sprintId = null;
        try{
            session.beginTransaction();

            List<Sprint> sprints = session.createQuery(
                    "FROM Sprint S WHERE S.name = "+sprint.getName()+
                            " AND S.endDate= "+sprint.getEndDate()+
                            " AND S.startDate= "+sprint.getStartDate()).list();
            if (sprints.isEmpty()) {
                sprintId = (Integer) session.save(sprint);
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return sprintId;
    }
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
}
