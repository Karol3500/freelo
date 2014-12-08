package org.freelo.model.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Artur on 12/8/2014.
 */

@Entity
@Table
public class GroupPrivileges {

    @Column
    private int groupID;

    @Column
    private int privilegeID;

    public int getGroupID() { return groupID; }

    public int getPrivilegeID() { return privilegeID; }

}
