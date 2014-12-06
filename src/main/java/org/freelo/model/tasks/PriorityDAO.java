package org.freelo.model.tasks;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

/**
 * Created by karol on 30.11.14.
 */
@Component
@Scope("singleton")
public class PriorityDAO {
    public static boolean savePriority(Priority p){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.save(p);
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
        finally{
            session.close();
        }
    }

    public static Priority getPriority(int id){
        Session session = HibernateSessionFactoryBean.getSession();
        Priority p = null;
        try {
            p = (Priority) session.get(Priority.class, id);
        }
        catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
        return p;
    }

}
