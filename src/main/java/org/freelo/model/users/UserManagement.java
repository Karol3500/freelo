package org.freelo.model.users;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@SuppressWarnings("unchecked")
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

			List<User> users = session.createQuery("FROM User").list();
            System.out.println();
            System.out.println("/n/nID/t name/t lastname/t email/t password/t date/t time/t/n");
            for (Iterator<User> iterator = users.iterator(); iterator.hasNext();){
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

            List<User> users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
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

            List<User> users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
            if (!users.isEmpty()) {
                user = (User) session.load(User.class, ((User) users.get(0)).getId());
                user.setPrivileges(user.getPrivileges());
            }

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

            List<User> users = session.createQuery("FROM User U WHERE U.id = "+UserID).list();
            if (!users.isEmpty()) {
                user = (User) session.load(User.class, UserID);
                user.setPrivileges(user.getPrivileges());
            }

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

            List<User> users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
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

    // add a new user, return user ID, null if adding user failed (eg. user with such email exist in DB)
    public static Integer userAdd(User user){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer userID = null;
        try{
            session.beginTransaction();

            List<User> users = session.createQuery("FROM User U WHERE U.email = '"+user.getEmail()+"'").list();
            if (users.isEmpty()) {
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

            List<User> users = session.createQuery("FROM User U WHERE U.email = '"+email+"'").list();
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

    // change basic information about the existing user
    public static void userUpdate(User user){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();

            User tempUser = (User)session.get(User.class, user.getId());
            if (tempUser != null) {
                tempUser.setName(user.getFirstName());
                tempUser.setLastName(user.getLastName());
                tempUser.setEmail(user.getEmail());
                tempUser.setPassword(user.getPassword());
                tempUser.setPrivileges(user.getPrivileges());
                session.update(tempUser);
            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public static void updatePrivileges(int ID, Set<Privilege> privileges){
        Session session = HibernateSessionFactoryBean.getSession();
        try {
            session.beginTransaction();

            User tempUser = (User) session.get(User.class, ID);
            if (tempUser != null) {
                tempUser.setPrivileges(privileges);
                session.update(tempUser);
            }

            session.getTransaction().commit();

        }catch (HibernateException e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

}


