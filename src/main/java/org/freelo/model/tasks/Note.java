package org.freelo.model.tasks;

import org.freelo.model.users.User;

import javax.persistence.*;

@Entity
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@OneToOne
	private User user;
	
	@Column
	private String priority;

    @Lob
    private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
}
