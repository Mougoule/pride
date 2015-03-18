package fr.pride.project.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@Table(name = "projet")
@Entity
@NamedQueries({ @NamedQuery(name = "Projet.findAll", query = "SELECT p FROM Projet p"),
				@NamedQuery(name = "Projet.findByNomProjet", query = "SELECT p FROM Projet p WHERE p.nomProjet = :NOM"),
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

	@Column(length = 1)
	private int noteProjet;

	/** Lob signal qu'il s'agit d'un grand blob */
	@Lob
	@Column
	private byte[] image;

	@OneToMany(targetEntity = Commentaire.class, mappedBy = "projet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Commentaire> commentaires;

	@OneToMany(targetEntity = Equipe.class, mappedBy = "projet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Equipe> equipes;

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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@JsonIgnore
	public Set<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(Set<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}
	
	public Set<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(Set<Equipe> equipes) {
		this.equipes = equipes;
	}

	public int getNoteProjet() {
		return noteProjet;
	}

	public void setNoteProjet(int noteProjet) {
		this.noteProjet = noteProjet;
	}
}
