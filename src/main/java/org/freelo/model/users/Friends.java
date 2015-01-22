package org.freelo.model.users;

import javax.persistence.*;

/**
 * Created by artur on 19.01.15.
 */

@Entity
@Table
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column
    private int userID;

    @Column
    private int friendID;

    public Friends(){}

    public Friends(int userID, int friendID){
        this.friendID = friendID;
        this.userID = userID;
    }

    public int getFriendID() {
        return friendID;
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }
}
