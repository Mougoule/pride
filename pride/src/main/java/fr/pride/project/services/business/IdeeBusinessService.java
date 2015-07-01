package fr.pride.project.services.business;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pride.project.model.Idee;
import fr.pride.project.model.IdeeId;
import fr.pride.project.model.Projet;
import fr.pride.project.model.Utilisateur;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.common.CustomError;
import fr.pride.project.services.rs.annotations.Tokenized;

@Service
public class IdeeBusinessService {
	
	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(IdeeBusinessService.class);
	
	@Autowired
	private UtilisateurBusinessService utilisateurBusinessService;

	@Autowired
	private ProjetBusinessService projetBusinessService;
	
	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Créer une idée pour un projet
	 * 
	 * @param login le login de l'utilisateur créant l'idée
	 * @param nomProjet le nom du projet associé à l'idée
	 * @param ideeString l'idée
	 * @throws BusinessException
	 */
	@Tokenized
	@Transactional
	public void createIdeeForProjet(String login, String nomProjet, String ideeString) throws BusinessException {
		
		LOGGER.info("Création d'une idée pour le couple {} / {} : ", nomProjet, login);

		Projet projet = projetBusinessService.getProjetByNomProjet(nomProjet);
		if (projet == null) {
			throw new BusinessException(CustomError.ERROR_PROJET_NOT_FOUND,
					"Impossible de créer l'idée. Aucun projet trouvé pour le nom de projet : " + nomProjet);
		}
		Utilisateur utilisateur = utilisateurBusinessService.getUtilisateurByLogin(login);
		if (utilisateur == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_NOT_FOUND,
					"Impossible de créer l'idée. Aucun utilisateur trouvé pour le login : " + login);
		}
		
		IdeeId ideeId = new IdeeId();
		ideeId.setDateCreation(new Date());
		ideeId.setIdProjet(nomProjet);
		ideeId.setIdUtilisateur(login);
		
		Idee idee = new Idee();
		idee.setId(ideeId);
		idee.setProjet(projet);
		idee.setUtilisateur(utilisateur);
		idee.setIdee(ideeString);
		
		em.persist(idee);
		Set<Idee> idees = new HashSet<Idee>();
		idees.add(idee);
		projet.setIdees(idees);
		em.merge(projet);
		
	}

}
