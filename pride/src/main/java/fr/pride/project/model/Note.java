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

@Table(name = "note")
@Entity
@NamedQueries({
		@NamedQuery(name = "Note.findByProjet", query = "SELECT n FROM Note n WHERE n.projet.nomProjet = :NOM"),
		@NamedQuery(name = "Note.findByUtilisateur", query = "SELECT n FROM Note n WHERE n.utilisateur.login = :LOGIN"),
})
@XmlRootElement(name = "note")
@XmlAccessorType(XmlAccessType.FIELD)
public class Note {
	
	@EmbeddedId
	private NoteId id;
	
	@Column(length = 1)
	private int note;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modification_note")
	private Date dateModification;
	
	@ManyToOne
	@JoinColumn(name = "id_utilisateur", nullable = false)
	@MapsId("idUtilisateur")
	private Utilisateur utilisateur;
	
	@ManyToOne
	@JoinColumn(name = "id_projet", nullable = false)
	@MapsId("idProjet")
	private Projet projet;

	/* Getters and Setters */

	public NoteId getId() {
		return id;
	}

	public void setId(NoteId id) {
		this.id = id;
	}
	
	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
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
