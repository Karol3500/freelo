package org.freelo.model.projects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.User;


@Entity
@Table
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column
    private int manager;   // project manager (user ID)

    @Column
    private String name;

    // todo list of the sprints
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="ID")
    private List<Sprint> sprints;

    @OneToMany(fetch=FetchType.EAGER)
    private List<User> users;


    public int getId() {
        return id;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    public void addSprint(Sprint sprint){
        sprints.add(sprint);
        ProjectManagement.updateProject(this);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User u){
        if(users == null){
            users = new ArrayList<User>();
        }
        users.add(u);
        ProjectManagement.updateProject(this);
    }

    public void removeUser(User u){
        if(users == null){
            users = new ArrayList<User>();
        }
        users.remove(u);
        ProjectManagement.updateProject(this);
    }
}