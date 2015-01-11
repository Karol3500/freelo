package org.freelo.model.projects;

import org.freelo.model.sprints.Sprint;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column
    private int manager;   // project manager (user ID)

    @Column
    private String name;

    @Column
    private Date start;

    @Column
    private Date end;

    // todo list of the sprints
    @OneToMany
    @JoinColumn(name = "ID")
    private Set<Sprint> sprints = new HashSet<Sprint>();


    public int getId() {
        return id;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(Set<Sprint> sprints) {
        this.sprints = new HashSet<Sprint>(sprints);
    }
}