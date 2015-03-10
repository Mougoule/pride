package fr.pride.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.pride.project.model.beans.ParameterKey;

/**
 * Paramètre applciatif
 * 
 *
 */
@Entity
@Table(name = "parameters")
public class Parameter {
	
	@Id
	@Column(name = "parameter_key")
	@Enumerated(EnumType.STRING)
	private ParameterKey key;
	
	@Column(name = "value")
	private String value;
	
	public ParameterKey getKey() {
		return key;
	}

	public void setKey(ParameterKey key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
