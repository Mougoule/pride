package fr.pride.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EquipeId implements Serializable{

	private static final long serialVersionUID = -4987177142646593300L;
	
	@Column(name = "id_utilisateur")
	private String idUtilisateur;
	
	@Column(name = "id_projet")
	private String idProjet;

	public String getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(String idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(String idProjet) {
		this.idProjet = idProjet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idProjet == null) ? 0 : idProjet.hashCode());
		result = prime * result
				+ ((idUtilisateur == null) ? 0 : idUtilisateur.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipeId other = (EquipeId) obj;
		if (idProjet == null) {
			if (other.idProjet != null)
				return false;
		} else if (!idProjet.equals(other.idProjet))
			return false;
		if (idUtilisateur == null) {
			if (other.idUtilisateur != null)
				return false;
		} else if (!idUtilisateur.equals(other.idUtilisateur))
			return false;
		return true;
	}
}
