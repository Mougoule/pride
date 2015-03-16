package fr.pride.project.services.business;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pride.project.model.Equipe;
import fr.pride.project.model.EquipeId;
import fr.pride.project.model.Projet;
import fr.pride.project.model.Utilisateur;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.common.CustomError;

@Service
public class EquipeBusinessService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(EquipeBusinessService.class);

	@Autowired
	private ProjetBusinessService projetBusinessService;

	@Autowired
	private UtilisateurBusinessService utilisateurBusinessService;

	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;

	public Equipe getEquipe(Utilisateur utilisateur, Projet projet) {
		LOGGER.info("Récupération d'une équipe par son identifiant : {}, {}", utilisateur.getLogin(),
				projet.getNomProjet());

		try {
			return em.createNamedQuery("Equipe.findById", Equipe.class).setParameter("PROJET", projet)
					.setParameter("UTILISATEUR", utilisateur).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public Equipe createEquipe(String login, String nomProjet) throws BusinessException {
		LOGGER.info("Création d'une équipe pour le couple {} / {} : ", nomProjet, login);

		Projet projet = projetBusinessService.getProjetByNomProjet(nomProjet);
		if (projet == null) {
			throw new BusinessException(CustomError.ERROR_PROJET_NOT_FOUND,
					"Impossible de créer l'équipe. Aucun projet trouvé pour le nom de projet : " + nomProjet);
		}
		Utilisateur utilisateur = utilisateurBusinessService.getUtilisateurByLogin(login);
		if (utilisateur == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_NOT_FOUND,
					"Impossible de créer le projet. Aucun utilisateur trouvé pour le login : " + login);
		}
		Equipe equipe = getEquipe(utilisateur, projet);
		if (equipe != null) {
			throw new BusinessException(CustomError.ERROR_EQUIPE_ALREADY_EXIST,
					"Impossible de créer l'équipe. Il existe déjà une équipe pour le couple projet / utilisateur : "
							+ nomProjet + " / " + login);
		}

		EquipeId equipeId = new EquipeId();
		equipeId.setIdProjet(nomProjet);
		equipeId.setIdUtilisateur(login);

		equipe = new Equipe();
		equipe.setId(equipeId);
		equipe.setProjet(projet);
		equipe.setUtilisateurs(utilisateur);

		em.persist(equipe);

		return equipe;
	}
}
