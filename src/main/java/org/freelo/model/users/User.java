package org.freelo.model.users;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
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

    @OneToMany
    private List<Privilege> privileges = new ArrayList<>();

	public User(){
		firstName = "";
		lastName = "";
		email = "";
		password = "";
		created = new Date();
	}

	public User(String fname, String lname, String email, String password){
		firstName = fname;
		lastName = lname;
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
	//public void setDate(Date createdDate) { this._created = createdDate; }

    //todo write code for below functions
    /*public void addPrivilege(int privilegeID) {
        for (Privilege element : privileges) {
            ;
        }
        //privileges.add()
    }
    public void deletePrivilege(int privilegeID) {}
    public List<Privilege> getPrivileges() { return privileges; }*/

}
