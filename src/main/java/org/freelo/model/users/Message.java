package org.freelo.model.users;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by artur on 19.01.15.
 */

@Entity
@Table
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column
    private int sender; // id of user which is sending the message

    @Column
    private int receiver; // id of user which is receiving the message

    @Lob
    private String message;

    @Column
    private Date date;

    @Column
    private boolean read;


    public Message(){
        this.sender = -1;
        this.receiver = -1;
        this.message = "";
        this.date =  new Date();
        this.read = false;
    }

    public Message(int sender, int receiver, String message){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date =  new Date();
        this.read = false;
    }


    public int getId() {
        return id;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public boolean isRead() {
        return read;
    }

    public void setAsRead() {
        this.read = true;
    }
}
