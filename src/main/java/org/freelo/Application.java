package org.freelo;

import org.freelo.model.sprints.SprintPrivileges;
import org.freelo.model.sprints.SprintPrivilegesManagement;
import org.freelo.model.users.PrivilegeManagement;
import org.freelo.model.users.User;
import org.freelo.model.users.UserManagement;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

        System.out.print("\n\n\n");
        UserManagement.userAdd("Artur","Waz","arturwaz@freelo.com","password");
        UserManagement.userAdd("Karol","Posila","karolposila@freelo.com","password");
        UserManagement.userAdd("Adrian","Cyga","adriancyga@freelo.com","password");
        UserManagement.userAdd("Konrad","Jazownik","konradjazownik@freelo.com","password");
        UserManagement.userAdd("Piotr","Bienias","piotrbienias@freelo.com","password");
        UserManagement.userAdd("Jan","Dziergwa","jandziergwa@freelo.com","password");
        UserManagement.userAdd("Ruben","Dias","rubendias@freelo.com","password");

        PrivilegeManagement.addPrivilege("admin");
        PrivilegeManagement.addPrivilege("project_deleting");
        PrivilegeManagement.addPrivilege("sprint_managing");
        PrivilegeManagement.addPrivilege("members_adding");
        PrivilegeManagement.addPrivilege("members_deleting");
        PrivilegeManagement.addPrivilege("task_adding");
        PrivilegeManagement.addPrivilege("task_deleting");

        /*SprintPrivilegesManagement.addSprintPrivileges(new SprintPrivileges("project_deleting"));
        SprintPrivilegesManagement.addSprintPrivileges(new SprintPrivileges("sprint_managing"));
        SprintPrivilegesManagement.addSprintPrivileges(new SprintPrivileges("members_adding"));
        SprintPrivilegesManagement.addSprintPrivileges(new SprintPrivileges("members_deleting"));
        SprintPrivilegesManagement.addSprintPrivileges(new SprintPrivileges("task_adding"));
        SprintPrivilegesManagement.addSprintPrivileges(new SprintPrivileges("task_deleting"));*/

        User setAdmin = UserManagement.getUser(1);
        setAdmin.addPrivilege(1);
        UserManagement.userUpdate(setAdmin);

    }
}
