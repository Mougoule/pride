package fr.pride.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * The persistent class for the customers database table.
 * 
 */
@Table(name = "users", indexes = { @Index(name = "idx_login", columnList = "login", unique = true) })
@Entity
@NamedQueries({
		@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u ORDER BY u.login"),
		@NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :LOGIN") })
public class User extends AbstractEntity {

	private static final long serialVersionUID = 2195440476922800924L;

	private String login;

	private String password;

	public User() {

	}

	@Column(name = "login", nullable = false)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}