package fr.pride.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Table(name = "projet")
@Entity
@NamedQueries({ @NamedQuery(name = "Projet.findAll", query = "SELECT p FROM Projet p"), })
@XmlRootElement(name = "projet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Projet implements Serializable {

	private static final long serialVersionUID = -294546755847949144L;

	@Id
	@Column(name = "nom_projet", unique = true, nullable = false, length = 255)
	private String nomProjet;

	@Column(columnDefinition = "TEXT")
	private String description;

	/** Lob signal qu'il s'agit d'un grand blob */
	@Lob
	@Column
	private byte[] image;

	@OneToMany(targetEntity = Commentaire.class, mappedBy = "projet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Commentaire> commentaires;

	@OneToOne(targetEntity = Equipe.class, mappedBy = "projet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Equipe equipe;

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

	public List<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
}
