package org.freelo.model.sprints;

import org.freelo.model.projects.Project;
import org.freelo.model.tasks.Note;
import org.freelo.model.tasks.NoteDAO;

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

    @ManyToOne
    private Project project;

    @Column
    private Date endDate;

    @Column
    private Date startDate;

    @Column
    private String leader;

    @Column
    private int sprintId;

    @Column
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Note> toDo = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Note> onGoing = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Note> done = new ArrayList<>();

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
        return done;
    }
    public void addDone(Note note){
        done.add(note);
    }

    public void addNoteToDo(Note n){
        toDo.add(n);
    }

    public void addNoteOngoing(Note n){
        onGoing.add(n);
    }

    public void addNoteDone(Note n){
        done.add(n);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public int hashCode() {
        return name.hashCode()+startDate.hashCode()+endDate.hashCode()+leader.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sprint))
            return false;
        if (obj == this)
            return true;
        Sprint s = (Sprint) obj;
        if(s.getStartDate()!=this.getStartDate() || s.getEndDate() != this.getEndDate()
                || s.getName() != this.getName()){
            return false;
        }
        return true;
    }
}
