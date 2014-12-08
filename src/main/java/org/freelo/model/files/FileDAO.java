package org.freelo.model.files;

/**
 * Created by Adrian on 08-12-2014.
 */
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;

import java.util.List;

public class FileDAO {

    public static UserFile getFile(int id){
        Session session = HibernateSessionFactoryBean.getSession();
        UserFile f = null;
        try{
            session.beginTransaction();
            f = (UserFile)session.get(UserFile.class,id);
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
        return f;
    }

    public static boolean saveFile(UserFile fileToSave){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.save(fileToSave);
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

    public static List<UserFile> getFilesBelongingToUser(int userId){
        Session session = HibernateSessionFactoryBean.getSession();
        List<UserFile> files = null;
        try {
            files = session.createQuery(
                    "FROM UserFile WHERE User.id = :userId")
                    .setParameter("userId", userId)
                    .list();
        }
        catch(Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        finally{
            session.close();
        }
        return files;
    }

}
