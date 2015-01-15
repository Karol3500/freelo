package org.freelo.model.users;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    public List<Privilege> getPrivileges() { return privileges; }
    public void setPrivileges(List<Privilege> privileges) { this.privileges = new ArrayList<Privilege>(privileges); }

}
