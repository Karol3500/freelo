package org.freelo;

import org.freelo.model.HibernateSessionFactoryBean;
import org.freelo.model.projects.Project;
import org.freelo.model.projects.ProjectManagement;
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



        // todo delete beforee commit
        System.out.print("\n\n\n\n\n\n\n\n\n\n");

        int manageID = UserManagement.getUserID("arturwaz@freelo.com");

        Project project = new Project();
        project.setManager(manageID);

        ProjectManagement.addProject(project);



        Project test = ProjectManagement.getProject(1);
        User manager = UserManagement.getUser(test.getManager());
        System.out.println(manager.getEmail());



        System.out.print("\n\n\n");
    }
}
