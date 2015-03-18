package fr.pride.project.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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

@Table(name = "equipe")
@Entity
@NamedQueries({
		@NamedQuery(name = "Equipe.findAll", query = "SELECT e FROM Equipe e"),
		@NamedQuery(name = "Equipe.findById", query = "SELECT e FROM Equipe e WHERE e.projet = :PROJET AND e.utilisateur = :UTILISATEUR"),
		@NamedQuery(name = "Equipe.findByProjet", query = "SELECT e FROM Equipe e WHERE e.projet = :PROJET"),
})
@XmlRootElement(name = "equipe")
@XmlAccessorType(XmlAccessType.FIELD)
public class Equipe {
	
	@EmbeddedId
	private EquipeId id;
	
	@ManyToOne
	@JoinColumn(name = "id_utilisateur", nullable = false)
	@MapsId("idUtilisateur")
	private Utilisateur utilisateur;
	
	@ManyToOne
	@JoinColumn(name = "id_projet", nullable = false)
	@MapsId("idProjet")
	private Projet projet;

	/* Getters and Setters */
	
	public EquipeId getId() {
		return id;
	}

	public void setId(EquipeId id) {
		this.id = id;
	}

	@JsonIgnore
	public Utilisateur getUtilisateurs() {
		return utilisateur;
	}

	public void setUtilisateurs(Utilisateur utilisateur) {
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
