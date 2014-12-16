package org.freelo.model.tasks;


import com.vaadin.shared.ui.colorpicker.Color;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Priority {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private Color color;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}	
}
