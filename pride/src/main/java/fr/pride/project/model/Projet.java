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


/**
 * Entité d'un projet
 */
@Table(name = "projet")
@Entity
@NamedQueries({ 
				@NamedQuery(name = "Projet.findByNomProjet", query = "SELECT p FROM Projet p WHERE p.nomProjet = :NOM"),
				@NamedQuery(name = "Projet.findAllByRoleAndLogin", query = 
						"SELECT p "
						+ "FROM "
							+ "Projet p "
						+ "WHERE p.nomProjet IN ( "
							+ "SELECT DISTINCT cp.nomProjet "
							+ "FROM "
								+ "Collaborateur c "
								+ "INNER JOIN "
									+ "c.utilisateur cu "
								+ "INNER JOIN "
									+ "c.projet cp "
								+ "WHERE "
									+ "c.role = :ROLE "
								+ "AND "
									+ "cu.login = :LOGIN)"),
		})
@XmlRootElement(name = "projet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Projet implements Serializable {

	private static final long serialVersionUID = -294546755847949144L;

	@Id
	@Column(name = "nom_projet", length = 255)
	private String nomProjet;

	@Column(columnDefinition = "TEXT")
	private String description;

	/**
	 * Association avec un commentaire
	 */
	@OneToMany(targetEntity = Commentaire.class, mappedBy = "projet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Commentaire> commentaires;

	/**
	 * Association avec un collaborateur
	 */
	@OneToMany(targetEntity = Collaborateur.class, mappedBy = "projet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Collaborateur> collaborateurs;
	
	/**
	 * Association avec une note
	 */
	@OneToMany(targetEntity = Note.class, mappedBy = "projet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Note> notes;
	
	@OneToMany(targetEntity = Idee.class, mappedBy = "projet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Idee> idees;

	/* Getters and Setters */
	
	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public Set<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(Set<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}
	
	@JsonIgnore
	public Set<Collaborateur> getCollaborateurs() {
		return collaborateurs;
	}

	public void setCollaborateurs(Set<Collaborateur> collaborateurs) {
		this.collaborateurs = collaborateurs;
	}

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	public Set<Idee> getIdees() {
		return idees;
	}

	public void setIdees(Set<Idee> idees) {
		this.idees = idees;
	}
}
