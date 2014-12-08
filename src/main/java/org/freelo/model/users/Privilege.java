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

    private int getID() { return ID; }

    private String getDescription() { return description; }
    private void setDescription(String newDescription) { description = newDescription; }

}
