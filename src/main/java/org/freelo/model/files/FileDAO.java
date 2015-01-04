package org.freelo.model.files;

/**
 * Created by Adrian on 08-12-2014.
 */
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.List;

public class FileDAO {

    public static UserFile getFile(long id){
        Session session = HibernateSessionFactoryBean.getSession();
        UserFile f = null;
        try{
            session.beginTransaction();
            f = (UserFile)session.get(UserFile.class, id);
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

    public static Integer saveFile(UserFile fileToSave){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer fileID = null;
        try{
            session.beginTransaction();

            List files = session.createQuery("FROM UserFile U WHERE U._fileName = '"+fileToSave.get_fileName()+"'").list();
            if (files.isEmpty()) {
                session.save(fileToSave);
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return fileID;
    }

    // delete file from DB
    public static void deleteFile(long id){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();

            List files = session.createQuery("FROM UserFile U WHERE U._ID = '"+ String.valueOf(id) +"'").list();
            if (!files.isEmpty()) {
                UserFile userFile = (UserFile)session.get(UserFile.class, ((UserFile) files.get(0)).get_ID());
                session.delete(userFile);
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public static List<UserFile> getFilesBelongingToUser(int userId){
        Session session = HibernateSessionFactoryBean.getSession();
        List<UserFile> files = null;
        try {
            files = session.createQuery(
                    "FROM UserFile WHERE UserFile.id = :userId")
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

    public static List<UserFile>  getFilesByUserName(String userName){
        Session session = HibernateSessionFactoryBean.getSession();
        List<UserFile> files = null;
        try{
            session.beginTransaction();

            files = session.createQuery("FROM UserFile UF WHERE UF._userName = '"+userName+"'").list();

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return files;
    }

}
