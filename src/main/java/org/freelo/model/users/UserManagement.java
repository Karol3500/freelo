package org.freelo.model.users;


import org.freelo.model.SessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

public class UserManagement {

    @Autowired
    SessionFactoryBean factoryBean;


    // change basic information about the user if ID is known
    public void testDisplay(int ID){
        Session session = factoryBean.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user =
                    (User)session.get(User.class, ID);

            //jak to kurwa wyswietlic...



        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }




    // add a new user
    public Integer userAdd(String fname, String lname, String email, String password){
        Session session = factoryBean.getSession();
        Transaction tx = null;
        Integer employeeID = null;
        try{
            tx = session.beginTransaction();
            User user = new User(fname, lname, email, password);
            employeeID = (Integer) session.save(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return employeeID;
    }



    // delete user from DB
    public void deleteUser(Integer EmployeeID){
        Session session = factoryBean.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user =
                    (User)session.get(User.class, EmployeeID);
            session.delete(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    // change basic information about the user if ID is known
    public void userUpdate(int ID, String fname, String lname, String email, String password){
        Session session = factoryBean.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user =
                    (User)session.get(User.class, ID);
            user.setName(fname);
            user.setLastName(lname);
            user.setEmail(email);
            user.setPassword(password);
            session.update(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

}


