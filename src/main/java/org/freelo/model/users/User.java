package org.freelo.model.users;

import org.freelo.model.tasks.Note;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.util.*;


@Entity
@Table
public class User{
    static String DEFAULT_PICTURE = "img/profile-pic-300px.jpg";
  
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "USER_ID")
	private int id;

	@Column
	private String email;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String password;

    @Column
    private String picturePath;

	@Column
	private Date created; // user's register time

    @ManyToMany
	@JoinTable(
			name = "UserPrivileges",
			joinColumns = {@JoinColumn(name = "USER_ID")},
			inverseJoinColumns =  {@JoinColumn(name = "PRIVILEGE_ID")}
	)
    private Set<Privilege> privileges = new HashSet<Privilege>();

	public User(){
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.password = "";
        this.picturePath = DEFAULT_PICTURE;
		this.created = new Date();
	}

	public User(String firstName, String lastName, String email, String password){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.created = new Date();
        this.picturePath = DEFAULT_PICTURE;
	}

	public int getId() {
		return id;
	}
	//public void setId(int id) { this._ID = id; }

	// please use this function to compare passwords
	public boolean passwordCompare(String passwordToCompare) {
		return password.equals(passwordToCompare);
	}

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getFirstName() { return firstName; }
	public void setName(String name) { this.firstName = name; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public Date getDate() { return created; }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void addUserToGroup(int groupID){
		List<Group> groups = GroupManagement.listGroups();
		for (Group element: groups){
			if (element.getID() == groupID){
				for (Privilege elem: element.getPrivileges()){
					addPrivilege(elem.getID());
				}
			}
		}
	}

    public void addPrivilege(int privilegeID) {
        for (Privilege element : privileges) {
            if (element.getID() == privilegeID)
				return;
        }
		Privilege privilege = PrivilegeManagement.getPrivilege(1);
		privileges.add(privilege);
    }
    public void deletePrivilege(int privilegeID) {
		Iterator<Privilege> setIterator = privileges.iterator();
		while (setIterator.hasNext()) {
			Privilege currentElement = setIterator.next();
			if (currentElement.getID() == privilegeID) {
				setIterator.remove();
			}
		}
	}
    public Set<Privilege> getPrivileges() { return privileges; }
	public void setPrivileges(Set<Privilege> privileges) { this.privileges = new HashSet<Privilege>(privileges); }

	@Override
	public boolean equals(Object secondUser){
		if(secondUser instanceof User){
			if((this.getEmail() == ((User) secondUser).getEmail()))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getEmail().hashCode();
	}
}
