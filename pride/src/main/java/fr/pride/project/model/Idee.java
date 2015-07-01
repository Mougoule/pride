package fr.pride.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Entité de l'idée
 */
@Table(name = "idee")
@Entity
@NamedQueries({
	@NamedQuery(name = "Idee.findByProjet", query = "SELECT i FROM Idee i WHERE i.projet.nomProjet = :NOM"),
	@NamedQuery(name = "Idee.findByUtilisateur", query = "SELECT i FROM Idee i WHERE i.utilisateur.login = :LOGIN"),
})
@XmlRootElement(name = "idee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Idee {

	/**
	 * Clef composite d'une idée
	 */
	@EmbeddedId
	private IdeeId id;
	
	@Column(columnDefinition = "TEXT")
	private String idee;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modification_idee")
	private Date dateModification;
	
	/**
	 * Association avec un utilisateur
	 */
	@ManyToOne
	@JoinColumn(name = "id_utilisateur", nullable = false)
	@MapsId("idUtilisateur")
	private Utilisateur utilisateur;
	
	/**
	 * Association avec un projet
	 */
	@ManyToOne
	@JoinColumn(name = "id_projet", nullable = false)
	@MapsId("idProjet")
	private Projet projet;
	
	/* Getters and Setters */
	
	public IdeeId getId() {
		return id;
	}

	public void setId(IdeeId id) {
		this.id = id;
	}
	
	public String getIdee() {
		return idee;
	}

	public void setIdee(String idee) {
		this.idee = idee;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	@JsonIgnore
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@JsonIgnore
	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}
}
