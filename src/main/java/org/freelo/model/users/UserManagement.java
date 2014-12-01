package org.freelo.model.users;


import org.freelo.model.SessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class UserManagement {

    @Autowired
    SessionFactoryBean factoryBean;


    // change basic information about the user if ID is known
    public void testDisplay(int ID){
        Session session = factoryBean.getSession();
        try{
            session.beginTransaction();
            User user =
                    (User)session.get(User.class, ID);

            //jak to kurwa wyswietlic...
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("/n/nID/t name/t lastname/t email/t password/t date/t time/t/n");
            System.out.println(user.getId()+" "+user.getFirstName()+" "+user.getLastName()+" "+user.getEmail()+" "+user.getPassword()+" "+user.getDate());


        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
    }




    // add a new user
    public Integer userAdd(String fname, String lname, String email, String password){
        Session session = factoryBean.getSession();
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
    public void deleteUser(Integer EmployeeID){
        Session session = factoryBean.getSession();
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
    public void userUpdate(int ID, String fname, String lname, String email, String password){
        Session session = factoryBean.getSession();
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


