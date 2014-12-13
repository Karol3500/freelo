package org.freelo.model.projects;

import javax.persistence.*;

/**
 * Created by piotr on 2014-12-08.
 */
@Entity
@Table
public class ProjectInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int ID;

    @OneToOne
    private Project project;

    @OneToOne
    private org.freelo.model.tasks.Note note;

    @Column
    private String statusOfTask;

    @Column
    private String taskList;



    public int getId() {
        return ID;
    }
    public void setId(int id) { this.ID = id; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public org.freelo.model.tasks.Note getNote() { return note; }
    public void setNote(org.freelo.model.tasks.Note note) { this.note = note; }

    public String getStatusOfTask() { return statusOfTask; }
    public void setStatusOfTask(String statusOfTask) { this.statusOfTask = statusOfTask; }

    public String getTaskList() { return taskList; }
    public void setTaskList(String taskList) { this.taskList = taskList; }

}
