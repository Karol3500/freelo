package org.freelo.model.tasks;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.Session;

/**
 * Created by karol on 30.11.14.
 */
public class ComponentDAO {

    static Session session = HibernateSessionFactoryBean.getSession();

    public static Component getComponent(int componentId){
        Component c = null;
        try {
            session.beginTransaction();
            c = (Component)session.get(Component.class, componentId);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            session.close();
        }
        return c;
    }
    public static boolean saveComponent(Component component){
        try{
            session.beginTransaction();
            session.save(component);
            session.getTransaction().commit();
        }
        catch(Exception ex){
            session.getTransaction().rollback();
        }
        return false;
    }
}
