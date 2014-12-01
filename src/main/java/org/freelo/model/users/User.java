package org.freelo.model.users;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import java.util.Date;


@Entity
@Table
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int _ID;

	@Column
	private String _email;

	@Column
	private String _firstName;

	@Column
	private String _lastName;

	@Column
	private String _password;

	@Column
	private Date _created; // user's register time

	public User(){
		_firstName = "";
		_lastName = "";
		_email = "";
		_password = "";
		_created = new Date();
	}

	public User(String fname, String lname, String email, String password){
		_firstName = fname;
		_lastName = lname;
		_email = email;
		_password = password;
		_created = new Date();
	}

	public int getId() {
		return _ID;
	}
	//public void setId(int id) { this._ID = id; }

	public String getEmail() { return _email; }
	public void setEmail(String email) { this._email = email; }

	public String getFirstName() { return _firstName; }
	public void setName(String name) { this._firstName = name; }

	public String getLastName() { return _lastName; }
	public void setLastName(String lastName) { this._lastName = lastName; }

	public String getPassword() { return _password; }
	public void setPassword(String password) { this._password = password; }

	public Date getDate() { return _created; }
	//public void setDate(Date createdDate) { this._created = createdDate; }

}
