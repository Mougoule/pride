package fr.pride.project.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@Table(name = "utilisateur")
@Entity
@NamedQueries({
		@NamedQuery(name = "Utilisateur.findByLogin", query = "SELECT u FROM Utilisateur u WHERE u.login = :LOGIN"),
		@NamedQuery(name = "Utilisateur.findAllNotInProjet", query = 
		"SELECT u "
		+ "FROM "
			+ "Utilisateur u "
		+ "WHERE u.login NOT IN ( "
			+ "SELECT DISTINCT cu.login "
			+ "FROM "
				+ "Collaborateur c "
				+ "INNER JOIN "
					+ "c.utilisateur cu "
				+ "INNER JOIN "
					+ "c.projet cp "
				+ "WHERE "
					+ "cp.nomProjet = :NOMPROJET)"),
})
@XmlRootElement(name = "utilisateur")
@XmlAccessorType(XmlAccessType.FIELD)
public class Utilisateur implements Serializable {

	private static final long serialVersionUID = 2227963945073192419L;
	
	@Id
	@Column(length = 255)
	private String login;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Column(nullable = false, length = 255)
	private String email;

	@OneToMany(targetEntity = Commentaire.class, mappedBy = "utilisateur", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Commentaire> commentaires;
	
	@OneToMany(targetEntity = Collaborateur.class, mappedBy = "utilisateur", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Collaborateur> collaborations;
	
	@OneToMany(targetEntity = Note.class, mappedBy = "utilisateur", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Note> notes;
	
	@OneToMany(targetEntity = Note.class, mappedBy = "utilisateur", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Idee> idees;

	/* Getters and Setters */
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public Set<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(Set<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	@JsonIgnore
	public Set<Collaborateur> getCollaborations() {
		return collaborations;
	}

	public void setCollaborations(Set<Collaborateur> collaborateurs) {
		this.collaborations = collaborateurs;
	}

	@JsonIgnore
	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	@JsonIgnore
	public Set<Idee> getIdees() {
		return idees;
	}

	public void setIdees(Set<Idee> idees) {
		this.idees = idees;
	}
}
