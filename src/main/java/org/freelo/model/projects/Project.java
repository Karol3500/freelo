package org.freelo.model.projects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
    @OneToMany(targetEntity=Sprint.class, mappedBy = "project", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Sprint> sprints = new ArrayList<Sprint>();

    @ManyToMany(fetch=FetchType.EAGER)
    private List<User> users = new ArrayList<User>();


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

    public void addSprint(Sprint sprint){
        sprints.add(sprint);
        sprint.setProject(this);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User u){
        users.add(u);
    }

    public void removeUser(User u){
        users.remove(u);
    }


    @Override
    public boolean equals(Object obj){
        if(obj instanceof Project){
            Project pr = (Project) obj;
            if((this.getName() == pr.getName() &&
                    this.getManager() == pr.getManager() &&
                    this.getUsers().hashCode() == pr.getUsers().hashCode()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + getUsers().hashCode() + getSprints().hashCode();
    }
}