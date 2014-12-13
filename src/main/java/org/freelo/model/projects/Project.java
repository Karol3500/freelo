package org.freelo.model.projects;

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
    private int ID;

    @Column
    private Date endDate;

    @Column
    private Date startDate;

    @Column
    private String leader;

    @Column
    private int projectId;

    @Column
    private String projectName;




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

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectid) { this.projectId = projectid; }

    public String getProjectDate() { return projectName; }
    public void setProjectDate(String projectname) { this.projectName = projectname; }

}
