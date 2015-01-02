package org.freelo.model.tasks;

import org.freelo.view.tasks.TaskCard;
import org.springframework.util.SerializationUtils;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by karol on 02.01.15.
 */
@Entity(name="TaskCard")
public class TaskCardDBO implements Serializable{

    @Column
    private byte[] tc;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Lob
    private byte[] getTaskCardAsByteArray() {
        return tc;
    }

    private void setTaskCardAsByteArray(byte[] tc) { // not exposed
        this.tc = tc;
    }

    public int getId(){
        return id;
    }

    public void setTaskCard(TaskCard tc){
        this.tc = SerializationUtils.serialize(tc);
    }

    @Transient
    public TaskCard getTaskCard() {
        return (TaskCard) SerializationUtils.deserialize(tc);
    }
}
