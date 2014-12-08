package org.freelo.model.Projects;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by piotr on 2014-12-08.
 */
@Entity
@Table
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int _ID;

    @Column
    private Date _endDate;

    @Column
    private Date _startDate;

    @Column
    private String _leader;

    @Column
    private int _projectId;

    @Column
    private String _projectName;




    public int getId() {
        return _ID;
    }
    public void setId(int id) { this._ID = id; }

    public Date getEndDate() { return _endDate; }
    public void setEndDate(Date enddate) { this._endDate = enddate; }

    public Date getStartDate() { return _startDate; }
    public void setStartDate(Date startdate) { this._startDate = startdate; }

    public String getLeader() { return _leader; }
    public void setLeader(String leader) { this._leader = leader; }

    public int getProjectId() { return _projectId; }
    public void setProjectId(int projectid) { this._projectId = projectid; }

    public String getProjectDate() { return _projectName; }
    public void setProjectDate(String projectname) { this._projectName = projectname; }

}
