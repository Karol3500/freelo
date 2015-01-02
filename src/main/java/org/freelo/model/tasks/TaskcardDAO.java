package org.freelo.model.tasks;

import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.view.tasks.TaskCard;
import org.hibernate.Session;

/**
 * Created by karol on 02.01.15.
 */
public class TaskcardDAO {
    public static TaskCard getTaskCard(int id){
        Session session = HibernateSessionFactoryBean.getSession();
        TaskCardDBO tc = null;
        try {
            session.beginTransaction();
            tc = (TaskCardDBO)session.get(TaskCardDBO.class, id);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            session.close();
        }
        return tc.getTaskCard();
    }

    public static boolean saveTaskCard(TaskCard tc){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            TaskCardDBO tcdbo=new TaskCardDBO();
            tcdbo.setTaskCard(tc);
            session.save(tcdbo);
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
}
