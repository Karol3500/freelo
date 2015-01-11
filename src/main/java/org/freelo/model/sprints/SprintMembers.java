package org.freelo.model.sprints;

import org.freelo.model.users.User;

import javax.persistence.*;

/**
 * Created by piotr on 2014-12-08.
 */
@Entity
@Table
public class SprintMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int ID;

    @OneToMany
    private User user;

    @OneToMany
    private Sprint sprint;


    public int getId() {
        return ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Sprint getSprint() { return sprint; }
    public void setProject(Sprint sprint) { this.sprint = sprint; }



}
