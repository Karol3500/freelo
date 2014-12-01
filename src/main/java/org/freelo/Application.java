package org.freelo;

import org.freelo.model.users.UserManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        /*System.out.println("\n\n\n\n\n");
        System.out.println("Meinen coden");
        System.out.println("\n\n\n\n\n");
        UserManagement test = new UserManagement();
        System.out.println("Added user: "+test.userAdd("art","waz","chuj","dupa"));
        System.out.println("Added user: "+test.userAdd("adsf", "sdf", "cffhuj", "duspa"));
        test.deleteUser(1);
        test.testDisplay(2);*/
    }
}
