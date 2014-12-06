package org.freelo.model.tasks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
public class Component {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	@Column
	@Lob
	protected String value;

	public int getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
}
