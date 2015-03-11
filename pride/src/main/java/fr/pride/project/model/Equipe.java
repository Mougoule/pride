package fr.pride.project.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Table(name = "equipe")
@Entity
@NamedQueries({
		@NamedQuery(name = "Equipe.findAll", query = "SELECT e FROM Equipe e"),
})
@XmlRootElement(name = "equipe")
@XmlAccessorType(XmlAccessType.FIELD)
public class Equipe {
	
	@EmbeddedId
	private EquipeId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur", nullable = false)
	@MapsId("idUtilisateur")
	private Utilisateur utilisateur;
	
	@OneToOne
	@JoinColumn(name = "id_projet", nullable = false, insertable = false, updatable = false)
	@MapsId("idProjet")
	private Projet projet;

	public EquipeId getId() {
		return id;
	}

	public void setId(EquipeId id) {
		this.id = id;
	}

	public Utilisateur getUtilisateurs() {
		return utilisateur;
	}

	public void setUtilisateurs(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	
	
	
}
