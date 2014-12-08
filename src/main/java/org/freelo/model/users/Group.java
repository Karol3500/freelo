package org.freelo.model.users;

import javax.persistence.*;

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

    //todo list of privileges for group

    public int getID() { return ID; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String newName) { groupName = newName; }

}
