package org.freelo;

import org.freelo.model.users.Privilege;
import org.freelo.model.users.PrivilegeManagement;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Configurable
@ComponentScan(basePackages="org.freelo")
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
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


        User user = UserManagement.getUser("arturwaz11@gmail.com");
        //user.addPrivilege(4);
        //user.setPrivileges(list);

        System.out.println("User Privileges:");
        for (Iterator iterator = list.iterator(); iterator.hasNext();){
            Privilege priv = (Privilege) iterator.next();
            System.out.println(priv.getID()+" "+priv.getDescription());
        }

        System.out.print("\n\n\n");
    }
}
