package org.freelo.model.sprints;

import org.freelo.model.users.User;

import javax.persistence.*;
import java.util.List;

/**
 * Created by piotr on 2014-12-08.
 */
@Entity
@Table
public class SprintMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @OneToMany
    private List<User> users;

    @OneToMany
    private List<Sprint> sprints;


    public int getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public List<Sprint> getSprints() {
        return sprints;
    }
    public void addSprint(Sprint sprint) {
        sprints.add(sprint);
    }


}
