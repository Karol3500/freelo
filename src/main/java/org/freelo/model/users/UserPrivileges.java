package org.freelo.model.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Artur on 12/8/2014.
 */

@Entity
@Table
public class UserPrivileges {

    @Column
    private int userID;

    @Column
    private int privilegeID;

    public int getUserID() { return userID; }

    public int getPrivilegeID() { return privilegeID; }

}
