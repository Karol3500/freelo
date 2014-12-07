package org.freelo.model.users;


import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import java.util.List;
import java.util.Iterator;

public class UserManagement {

    // only for test do not use in final program !!!
    public void testDisplay(){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();

            System.out.println();
            System.out.println();
            System.out.println();
            //System.out.println(user.getId()+" "+user.getFirstName()+" "+user.getLastName()+" "+user.getEmail()+" "+user.getPassword()+" "+user.getDate());

            List users = session.createQuery("FROM User").list();
            System.out.println();
            System.out.println("/n/nID/t name/t lastname/t email/t password/t date/t time/t/n");
            for (Iterator iterator = users.iterator(); iterator.hasNext();){
                User user = (User) iterator.next();
                System.out.println(user.getId()+" "+user.getFirstName()+" "+user.getLastName()+" "+user.getEmail()+" "+user.getPassword()+" "+user.getDate());
            }

        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    // returns user ID if found, null if user doesnt exist
    public static Integer getUserID(String email){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer user = null;
        try{
            session.beginTransaction();

            List users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
            if (!users.isEmpty())
                user = ((User) users.get(0)).getId();

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return user;
    }

    // returns User if found, null if user doesnt exist
    public static User getUser(String email){
        Session session = HibernateSessionFactoryBean.getSession();
        User user = null;
        try{
            session.beginTransaction();

            List users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
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

    // returns User if found, null if user doesnt exist
    public static User getUser(Integer UserID){
        Session session = HibernateSessionFactoryBean.getSession();
        User user = null;
        try{
            session.beginTransaction();

            List users = session.createQuery("FROM User U WHERE U.id = "+UserID).list();
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

    // add a new user, return user ID, null if adding user failed (eg. user with such email exist in DB)
    public static Integer userAdd(String fname, String lname, String email, String password){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer userID = null;
        try{
            session.beginTransaction();

            List users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
            if (users.isEmpty()) {
                User user = new User(fname, lname, email, password);
                userID = (Integer) session.save(user);
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return userID;
    }

    // delete user from DB
    public static void deleteUser(String email){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();

            List users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
            if (!users.isEmpty()) {
                User user = (User)session.get(User.class, ((User) users.get(0)).getId());
                session.delete(user);
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    // delete user from DB
    public static void deleteUser(Integer UserID){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();


            User user = (User)session.get(User.class, UserID);
            session.delete(user);

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    // change basic information about the user if ID is known
    public static void userUpdate(int ID, String fname, String lname, String email, String password){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();

            User user = (User)session.get(User.class, ID);
            user.setName(fname);
            user.setLastName(lname);
            user.setEmail(email);
            user.setPassword(password);
            session.update(user);

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

}


