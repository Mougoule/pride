package fr.pride.project.services.business;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pride.project.model.Collaborateur;
import fr.pride.project.model.Projet;
import fr.pride.project.model.Utilisateur;
import fr.pride.project.model.enums.Role;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.common.CustomError;
import fr.pride.project.services.rs.annotations.Tokenized;

@Service
public class ProjetBusinessService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjetBusinessService.class);

	@Autowired
	private UtilisateurBusinessService utilisateurBusinessService;

	@Autowired
	private CollaborateurBusinessService collaborateurBusinessService;

	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;

	@Tokenized
	public Projet getProjetByNomProjet(String nomProjet) {
		LOGGER.info("Récupération d'un projet par son nom : {}", nomProjet);
		try {
			return em.createNamedQuery("Projet.findByNomProjet", Projet.class).setParameter("NOM", nomProjet)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Tokenized
	@Transactional
	public void creerProjet(String login, String nomProjet, String description) throws BusinessException {
		LOGGER.info("Création d'un projet : {}, par : {}", nomProjet, login);

		if (utilisateurBusinessService.getUtilisateurByLogin(login) == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_NOT_FOUND,
					"Impossible de créer le projet. Aucun utilisateur trouvé pour le login : " + login);
		}
		if (getProjetByNomProjet(nomProjet) != null) {
			throw new BusinessException(CustomError.ERROR_PROJET_ALREADY_EXIST,
					"Impossible de créer le projet. Un projet existe déjà pour le nom : " + nomProjet);
		}

		Projet projet = new Projet();
		projet.setNomProjet(nomProjet);
		projet.setDescription(description);
		em.persist(projet);

		Set<Collaborateur> collaborateur = new HashSet<Collaborateur>();
		collaborateur.add(collaborateurBusinessService.createCollaborateur(login, nomProjet, Role.CHEF));

		projet.setCollaborateurs(collaborateur);

		em.merge(projet);
	}

	@Tokenized
	public List<Projet> findAllByLoginAndRole(String login, String roleStr) throws BaseException {
		LOGGER.info("Récupération des projets pour l'utilisateur : {}, pour le role : {}", login, roleStr);
		Utilisateur utilisateur = utilisateurBusinessService.getUtilisateurByLogin(login);

		if (utilisateur == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_NOT_FOUND,
					"Impossible de récupérer les projets, l'utilisateur : " + login + " n'existe pas");
		}
		Role role;
		if (Role.valueOf(roleStr) == Role.CHEF) {
			role = Role.CHEF;
		} else if (Role.valueOf(roleStr) == Role.COLLABO) {
			role = Role.CHEF;
		} else {
			throw new BusinessException(CustomError.ERROR_ROLE_DOES_NOT_EXIST,
					"Impossible de récupérer les projets, le rôle n'existe pas : " + roleStr);
		}
		LOGGER.info("Le rôle reçu : {}", role.getRole());
		List<Projet> projets = em.createNamedQuery("Projet.findAllByRoleAndLogin", Projet.class)
				.setParameter("ROLE", role).setParameter("LOGIN", login).getResultList();
		return projets;
	}

	public List<Utilisateur> findAllCollaborateurByProjet(String nomProjet) throws BaseException {
		LOGGER.info("Récupération des collaborateurs pour le projet : {}", nomProjet);

		Projet projet = getProjetByNomProjet(nomProjet);
		if (projet == null) {
			throw new BusinessException(CustomError.ERROR_PROJET_NOT_FOUND,
					"Impossible de récupérer les collaborateurs, le projet : " + nomProjet + " n'existe pas");
		}

		List<Utilisateur> utilisateurs = em
				.createNamedQuery("Collaborateur.findUtilisateurByProjet", Utilisateur.class)
				.setParameter("PROJET", projet).getResultList();
		return utilisateurs;
	}
}
