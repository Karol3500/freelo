package org.freelo.model.users;

import javax.persistence.*;

/**
 * Created by Artur on 12/8/2014.
 */

@Entity
@Table
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int ID;

    @Column
    private String description;

    public int getID() { return ID; }

    public String getDescription() { return description; }
    public void setDescription(String newDescription) { description = newDescription; }

}
