package org.freelo.model.tasks;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.freelo.model.users.User;
import org.freelo.view.tasks.TaskCard;

@Entity
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@OneToOne
	private User user;
	
//	@OneToOne
//	private Priority priority;

    @Column
    private TaskCard tc;
	
//	@OneToMany
//	private List<Component> components = new ArrayList<>();

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
//
//	public Priority getPriority() {
//		return priority;
//	}
//
//	public void setPriority(Priority priority) {
//		this.priority = priority;
//	}

    public TaskCard getTc() {
        return tc;
    }

    public void setTc(TaskCard tc) {
        this.tc = tc;
    }

    //	public List<Component> getComponents() {
//		return components;
//	}
//	public void addComponent(Component component){
//		components.add(component);
//	}

}
