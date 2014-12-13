package org.freelo.model.users;

import org.freelo.model.tasks.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur on 12/8/2014.
 */

@Entity
@Table
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int ID;

    @Column
    private String groupName;

    @OneToMany
    private List<Privilege> privileges = new ArrayList<>();



    public int getID() { return ID; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String newName) { groupName = newName; }

    //todo write code for below functions
    public void addPrivilege(int privilegeID) {}
    public void deletePrivilege(int privilegeID) {}
    public List<Privilege> getPrivileges() { return privileges; }

}
