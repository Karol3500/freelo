package org.freelo.model.users;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.util.*;


@Entity
@Table
public class User {

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
		this.created = new Date();
	}

	public User(String fname, String lname, String email, String password){
		this.firstName = fname;
		this.lastName = lname;
		this.email = email;
		this.password = password;
		created = new Date();
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
	// public void setDate(Date createdDate) { this._created = createdDate; }

    // todo write code for below functions
    public void addPrivilege(int privilegeID) {
        for (Privilege element : privileges) {
            //if (element.getID() == privilegeID)
				//return;
			System.out.println(element.getID()+" "+element.getDescription());
        }
        //privileges.add()
    }
    public void deletePrivilege(int privilegeID) {}
    public Set<Privilege> getPrivileges() { return privileges; }
	public void setPrivileges(Set<Privilege> privileges) { this.privileges = privileges; }

}
