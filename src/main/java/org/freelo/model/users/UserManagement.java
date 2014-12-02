package org.freelo.model.users;


import org.freelo.model.HibernateSessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
//import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Iterator;

public class UserManagement {
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
        }finally {
            session.close();
        }
    }


    public static User getUserByMail(String email){
        Session session = HibernateSessionFactoryBean.getSession();
        User user = new User();
        try{
            session.beginTransaction();

            List users = session.createQuery("FROM User").list();
            for (Iterator iterator = users.iterator(); iterator.hasNext();){

                User temp = (User) iterator.next();
                if (email.equals(temp.getEmail())) {
                    user = temp;
                    break;
                }

            }

            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return user;
    }


    // add a new user
    public static Integer userAdd(String fname, String lname, String email, String password){
        Session session = HibernateSessionFactoryBean.getSession();
        Integer employeeID = null;
        try{
            session.beginTransaction();
            User user = new User(fname, lname, email, password);
            employeeID = (Integer) session.save(user);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return employeeID;
    }



    // delete user from DB
    public static void deleteUser(Integer EmployeeID){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();
            User user =
                    (User)session.get(User.class, EmployeeID);
            session.delete(user);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    // change basic information about the user if ID is known
    public static void userUpdate(int ID, String fname, String lname, String email, String password){
        Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();
            User user =
                    (User)session.get(User.class, ID);
            user.setName(fname);
            user.setLastName(lname);
            user.setEmail(email);
            user.setPassword(password);
            session.update(user);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

}


