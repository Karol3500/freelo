package org.freelo.model.projects;

import org.freelo.model.users.User;

import javax.persistence.*;

/**
 * Created by piotr on 2014-12-08.
 */
@Entity
@Table
public class ProjectMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int _ID;

    @OneToOne
    private User user;

    @OneToOne
    private Project project;


    public int getId() {
        return _ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }



}
