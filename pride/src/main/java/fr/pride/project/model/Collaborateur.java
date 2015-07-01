package fr.pride.project.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

import fr.pride.project.model.enums.Role;

/**
 * Entité collaborateur
 */
@Table(name = "collaborateur")
@Entity
@NamedQueries({
		@NamedQuery(name = "Collaborateur.findAll", query = "SELECT c FROM Collaborateur c"),
		@NamedQuery(name = "Collaborateur.findById", query = "SELECT c FROM Collaborateur c WHERE c.projet = :PROJET AND c.utilisateur = :UTILISATEUR"),
		@NamedQuery(name = "Collaborateur.findByProjet", query = "SELECT c FROM Collaborateur c WHERE c.projet = :PROJET"),
		@NamedQuery(name = "Collaborateur.findUtilisateurByProjet", 
			query = 
				"SELECT u "
				+ "FROM "
					+ "Utilisateur u "
				+ "WHERE "
					+ "u.login IN ( "
						+ "SELECT DISTINCT "
							+ "	c.utilisateur.login "
						+ "FROM "
							+ "Collaborateur c "
						+ "WHERE "
							+ "c.projet = :PROJET)"),
})
@XmlRootElement(name = "collaborateur")
@XmlAccessorType(XmlAccessType.FIELD)
public class Collaborateur {
	
	/**
	 * Clef primaire composite
	 */
	@EmbeddedId
	private CollaborateurId id;
	
	/**
	 * Association
	 */
	@ManyToOne
	@JoinColumn(name = "id_utilisateur", nullable = false)
	@MapsId("idUtilisateur")
	private Utilisateur utilisateur;
	
	/**
	 * Association
	 */
	@ManyToOne
	@JoinColumn(name = "id_projet", nullable = false)
	@MapsId("idProjet")
	private Projet projet;
	
	/**
	 * Enum car il n'y as que deux rôles possibles (Chef d'équipe et collaborateur)
	 */
	@Enumerated(EnumType.STRING)
	private Role role;

	/* Getters and Setters */
	
	public CollaborateurId getId() {
		return id;
	}

	public void setId(CollaborateurId id) {
		this.id = id;
	}

	@JsonIgnore
	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	@JsonIgnore
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
