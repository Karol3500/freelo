package org.freelo.model.tasks;

import org.freelo.view.tasks.TaskCard;

import javax.persistence.*;


/**
 * Created by karol on 02.01.15.
 */
@Entity(name="TaskCard")
public class TaskCardDBO {

    @Column
    private TaskCard tc;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    public int getId(){
        return id;
    }

    public void setTaskCard(TaskCard tc){
        this.tc = tc;
    }

    public TaskCard getTaskCard(){
        return tc;
    }
}
