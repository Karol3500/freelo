package org.freelo.model.sprints;

import org.freelo.model.tasks.Note;

import javax.persistence.*;

import java.util.*;
/**
 * Created by piotr on 2014-12-08.
 */
@Entity
@Table
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int ID;

    @Column
    private Date endDate;

    @Column
    private Date startDate;

    @Column
    private String leader;

    @Column
    private int sprintId;

    @Column
    private String sprintName;

    @OneToMany
    @JoinColumn(name="id")

     private List<Note> toDo = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="id")

    private List<Note> onGoing = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="id")
    private List<Note> Done = new ArrayList<>();



    public void addTask(int taskID) {}
    public void deleteTask(int taskID) {}


    public int getId() {
        return ID;
    }
    public void setId(int id) { this.ID = id; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date enddate) { this.endDate = enddate; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startdate) { this.startDate = startdate; }

    public String getLeader() { return leader; }
    public void setLeader(String leader) { this.leader = leader; }

    public int getSprintId() { return sprintId; }
    public void setSprintId(int sprintId) { this.sprintId = sprintId; }

    public String getSprint() { return sprintName; }
    public void setSprint(String sprintname) { this.sprintName = sprintname; }


    public List<Note> getToDo() {
        return toDo;
    }
    public void addToDo(Note note){
        toDo.add(note);
    }

    public List<Note> getOnGoing() {
        return onGoing;
    }
    public void addOnGoing(Note note){
        onGoing.add(note);
    }

    public List<Note> getDone() {
        return Done;
    }
    public void addDone(Note note){
        Done.add(note);
    }

}
