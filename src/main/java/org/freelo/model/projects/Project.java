package org.freelo.model.projects;

import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.User;

import javax.persistence.*;
import java.util.*;


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

    @Column
    private Date start;

    @Column
    private Date end;

    // todo list of the sprints
    @OneToMany
    private List<Sprint> sprints;

    @OneToMany
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
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