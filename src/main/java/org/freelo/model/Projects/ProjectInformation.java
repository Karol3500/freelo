package org.freelo.model.Projects;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by piotr on 2014-12-08.
 */
@Entity
@Table
public class ProjectInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int _ID;

    @OneToOne
    private Project project;

    @OneToOne
    private org.freelo.model.tasks.Note note;

    @Column
    private String _statusOfTask;

    @Column
    private String _taskList;



    public int getId() {
        return _ID;
    }
    public void setId(int id) { this._ID = id; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public org.freelo.model.tasks.Note getNote() { return note; }
    public void setNote(org.freelo.model.tasks.Note note) { this.note = note; }

    public String getStatusOfTask() { return _statusOfTask; }
    public void setStatusOfTask(String statusOfTask) { this._statusOfTask = statusOfTask; }

    public String getTaskList() { return _taskList; }
    public void setTaskList(String taskList) { this._taskList = taskList; }

}
