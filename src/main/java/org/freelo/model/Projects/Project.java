package org.freelo.model.Projects;

import javax.persistence.Entity;
import javax.persistence.Table;

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

    public String getEndDate() { return _endDate; }
    public void setEndDate(String enddate) { this._endDate = enddate; }

    public String getStartDate() { return _startDate; }
    public void setStartDate(String startdate) { this._startDate = startdate; }

    public String getLeader() { return _leader; }
    public void setLeader(String leader) { this._leader = leader; }

    public String getProjectId() { return _projectId; }
    public void setProjectId(String projectid) { this._projectId = projectid; }

    public Date getProjectDate() { return _projectName; }
    public void setProjectDate(String projectname) { this._projectName = projectname; }

}
