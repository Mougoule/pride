package fr.pride.project.services.business;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pride.project.model.Collaborateur;
import fr.pride.project.model.CollaborateurId;
import fr.pride.project.model.Projet;
import fr.pride.project.model.Utilisateur;
import fr.pride.project.model.enums.Role;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.common.CustomError;
import fr.pride.project.services.rs.annotations.Tokenized;

@Service
public class CollaborateurBusinessService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(CollaborateurBusinessService.class);

	@Autowired
	private ProjetBusinessService projetBusinessService;

	@Autowired
	private UtilisateurBusinessService utilisateurBusinessService;

	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;

	@Tokenized
	public Collaborateur getCollaborateur(Utilisateur utilisateur, Projet projet) {
		LOGGER.info("Récupération d'une équipe par son identifiant : {}, {}", utilisateur.getLogin(),
				projet.getNomProjet());

		try {
			return em.createNamedQuery("Collaborateur.findById", Collaborateur.class).setParameter("PROJET", projet)
					.setParameter("UTILISATEUR", utilisateur).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Tokenized
	@Transactional
	public Collaborateur createCollaborateur(String login, String nomProjet, Role role) throws BusinessException {
		LOGGER.info("Création d'une équipe pour le couple {} / {} : ", nomProjet, login);

		Projet projet = projetBusinessService.getProjetByNomProjet(nomProjet);
		if (projet == null) {
			throw new BusinessException(CustomError.ERROR_PROJET_NOT_FOUND,
					"Impossible de créer l'équipe. Aucun projet trouvé pour le nom de projet : " + nomProjet);
		}
		Utilisateur utilisateur = utilisateurBusinessService.getUtilisateurByLogin(login);
		if (utilisateur == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_NOT_FOUND,
					"Impossible de créer l'équipe. Aucun utilisateur trouvé pour le login : " + login);
		}
		Collaborateur collaborateur = getCollaborateur(utilisateur, projet);
		if (collaborateur != null) {
			throw new BusinessException(CustomError.ERROR_EQUIPE_ALREADY_EXIST,
					"Impossible de créer l'équipe. Il existe déjà une équipe pour le couple projet / utilisateur : "
							+ nomProjet + " / " + login);
		}

		CollaborateurId collaborateurId = new CollaborateurId();
		collaborateurId.setIdProjet(nomProjet);
		collaborateurId.setIdUtilisateur(login);

		collaborateur = new Collaborateur();
		collaborateur.setId(collaborateurId);
		collaborateur.setProjet(projet);
		collaborateur.setUtilisateur(utilisateur);
		collaborateur.setRole(role);

		em.persist(collaborateur);

		return collaborateur;
	}
}
