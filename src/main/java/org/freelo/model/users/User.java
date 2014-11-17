package org.freelo.model.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User {
	@Id @GeneratedValue
	private int id;

	public int getId() {
		return id;
	}
}
