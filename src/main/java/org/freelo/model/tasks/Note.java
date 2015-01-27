package org.freelo.model.tasks;

import org.freelo.model.sprints.Sprint;
import org.freelo.model.users.User;

import javax.persistence.*;

@Embeddable
public class Note {

	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
	@Column
	private String priority;

	@Column
	private String taskName;

    @Lob
    private String text;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


    public String getText() {
        return text;
    }

    public void setText(String tc) {
        this.text = tc;
    }


	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public int hashCode() {
		return user.getEmail().hashCode()+taskName.hashCode()+text.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Note))
			return false;
		if (obj == this)
			return true;
		Note n = (Note) obj;
		if(n.getUser().getId()!=this.getUser().getId()||
				n.getTaskName()!=this.getTaskName()){
			return false;
		}
		return true;
	}
}
