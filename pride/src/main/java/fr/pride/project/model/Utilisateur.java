package fr.pride.project.model;

import java.io.Serializable;
import java.util.List;

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

@Table(name = "utilisateur")
@Entity
@NamedQueries({
		@NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u"),
		@NamedQuery(name = "Utilisateur.findByLogin", query = "SELECT u FROM Utilisateur u WHERE u.login = :LOGIN"),
})
@XmlRootElement(name = "utilisateur")
@XmlAccessorType(XmlAccessType.FIELD)
public class Utilisateur implements Serializable {

	private static final long serialVersionUID = 2227963945073192419L;
	
	@Id
	@Column(nullable = false, unique = true, length = 255)
	private String login;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Column(nullable = false, length = 255)
	private String email;
	
	@Column(nullable = false, length = 255)
	private String pseudo;

	@OneToMany(targetEntity = Commentaire.class, mappedBy = "utilisateur", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Commentaire> commentaires;
	
	@OneToMany(targetEntity = Equipe.class, mappedBy = "utilisateur", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Equipe> equipes;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public List<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	public List<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}
}
