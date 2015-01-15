package org.freelo.model.users;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Created by Artur on 12/8/2014.
 */

@Entity
@Table
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "PRIVILEGE_ID")
    private int ID;

    @Column
    private String description;

    @ManyToMany(mappedBy="privileges")
    Set<User> users = new HashSet<User>();


    public Privilege(){}
    public Privilege(String description){ this.description = description; }

    public int getID() { return ID; }

    public String getDescription() { return description; }
    public void setDescription(String newDescription) { description = newDescription; }

}
