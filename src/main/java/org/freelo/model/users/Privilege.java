package org.freelo.model.users;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
