package org.freelo;

import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.users.Privilege;
import org.freelo.model.users.PrivilegeManagement;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Configurable
@ComponentScan(basePackages="org.freelo")
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableTransactionManagement
@RequestMapping("/")
public class Application {
    public static void main(String[] args) {
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        System.setProperty("server.port", webPort);

        SpringApplication.run(Application.class, args);

        //todo delete in final version
        // do not delete this part, it is only for testing
        System.out.print("\n\n\n");
        UserManagement manage = new UserManagement();
        manage.userAdd("Artur","Waz","arturwaz@freelo.com","password");
        manage.userAdd("Karol","Posila","karolposila@freelo.com","password");
        manage.userAdd("Adrian","Cyga","adriancyga@freelo.com","password");
        manage.userAdd("Konrad","Jazownik","konradjazownik@freelo.com","password");
        manage.userAdd("Piotr","Bienias","piotrbienias@freelo.com","password");
        manage.userAdd("Jan","Dziergwa","jandziergwa@freelo.com","password");
        manage.userAdd("Ruben","Dias","rubendias@freelo.com","password");



        System.out.print("\n\n\n\n\n\n\n\n\n\n");


        PrivilegeManagement.addPrivilege("taki tam test");

        List<Privilege> list = PrivilegeManagement.listPrivileges();
        for (Iterator iterator = list.iterator(); iterator.hasNext();){
            Privilege priv = (Privilege) iterator.next();
            System.out.println(priv.getID()+" "+priv.getDescription());
        }
        PrivilegeManagement.addPrivilege("taki tam drugi test");
        list = PrivilegeManagement.listPrivileges();
        for (Iterator iterator = list.iterator(); iterator.hasNext();){
            Privilege priv = (Privilege) iterator.next();
            System.out.println(priv.getID()+" "+priv.getDescription());
        }
        System.out.print("\n\n\n");


        User user = UserManagement.getUser("arturwaz@freelo.com");
        System.out.println("User chosen.");
        //user.addPrivilege(4);
        //user.setPrivileges(list);
        //User user = new User();
        //System.out.println(user.getId());


        Set<Privilege> set = new HashSet<Privilege>();
        Privilege temp = PrivilegeManagement.getPrivilege(1);
        set.add(temp);

        temp = PrivilegeManagement.getPrivilege(2);
        set.add(temp);


        user.setPrivileges(set);

        UserManagement.userUpdate(user);

        System.out.println("User Privileges:");
        for (Iterator iterator = user.getPrivileges().iterator(); iterator.hasNext();) {
            Privilege priv = (Privilege) iterator.next();
            System.out.println(priv.getID() + " " + priv.getDescription());
        }



        /*System.out.print("\n\n\n");
        list = PrivilegeManagement.listPrivileges();
        for (Iterator iterator = list.iterator(); iterator.hasNext();){
            Privilege priv = (Privilege) iterator.next();
            System.out.println(priv.getID()+" "+priv.getDescription());
        }*/
        System.out.print("\n\n\n");

        /*Session session = HibernateSessionFactoryBean.getSession();
        session.beginTransaction();
        User user3 = (User) session.load(User.class, 1);
        System.out.println(user3.getId()+" "+user3.getFirstName()+" "+user3.getLastName()+" "+user3.getEmail());
        session.getTransaction().commit();
        //session.close();*/



        User user3 = UserManagement.getUser(1);

        System.out.println(user3.getId()+" "+user3.getFirstName()+" "+user3.getLastName()+" "+user3.getEmail());
        for (Iterator iterator = user3.getPrivileges().iterator(); iterator.hasNext();) {
            Privilege priv = (Privilege) iterator.next();
            System.out.println(priv.getID() + " " + priv.getDescription());
        }

        /*Session session = HibernateSessionFactoryBean.getSession();
        try{
            session.beginTransaction();

            List pr = session.createQuery("FROM UserPrivileges").list();

            for (Iterator iterator = pr.iterator(); iterator.hasNext();){
                Privilege temppr = (Privilege) iterator.next();
                System.out.println(temppr.getID()+" "+temppr.getDescription());
            }

        }catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }*/


        System.out.print("\n\n\n");
    }
}
