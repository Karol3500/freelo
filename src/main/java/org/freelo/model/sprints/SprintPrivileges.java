package org.freelo.model.sprints;

import javax.persistence.*;

/**
 * Created by piotr on 2014-12-08.
 */
public class SprintPrivileges {


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int ID;

    /*@OneToMany
    private User user;
    */

    @Column
    private String description;

    public int getID() {
        return ID;
    }



    public String getDescription() { return description; }
    public void setDescription(String newDescription) { description = newDescription; }


   /* public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    */
}
