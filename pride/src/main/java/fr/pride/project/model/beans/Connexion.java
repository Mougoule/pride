package fr.pride.project.model.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import fr.pride.project.model.Utilisateur;

/**
 * Bean de connexion. Sert à transmettre l'utilisateur qui se connecte (pour
 * pouvoir récupérer ses infos dans le client et le token
 */
@XmlRootElement(name = "connexion")
@XmlAccessorType(XmlAccessType.FIELD)
public class Connexion {

	private Utilisateur utilisateur;

	private String token;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur livreur) {
		this.utilisateur = livreur;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
