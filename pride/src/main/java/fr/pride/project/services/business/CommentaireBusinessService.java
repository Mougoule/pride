package fr.pride.project.services.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pride.project.model.Commentaire;
import fr.pride.project.model.Projet;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.common.CustomError;

@Service
public class CommentaireBusinessService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentaireBusinessService.class);

	@Autowired
	private ProjetBusinessService projetBusinessService;
	
	@Autowired
	private UtilisateurBusinessService utilisateurBusinessService;
	
	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void creerCommentaire(Commentaire commentaire) throws BusinessException{
		String login = commentaire.getUtilisateur().getLogin();
		String nomProjet = commentaire.getProjet().getNomProjet();
		LOGGER.info("Création d'un commentaire pour l'utilisateur {} et pour le projet {}", login, nomProjet);
		if (projetBusinessService.getProjetByNomProjet(nomProjet) == null) {
			throw new BusinessException(CustomError.ERROR_PROJET_NOT_FOUND,
					"Impossible de créer le commentaire. Aucun projet trouvé pour le nom : " + nomProjet);
		}
		if (utilisateurBusinessService.getUtilisateurByLogin(login) == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_NOT_FOUND,
					"Impossible de créer le commentaire. Aucun utilisateur trouvé pour le login : " + login);
		}
		
		em.merge(commentaire);
	}

	public List<Commentaire> getCommentaireByProjet(Projet projet) {
		LOGGER.info("Récupération des commentaires pour le projet {}", projet.getNomProjet());
		List<Commentaire> commentaires = em.createNamedQuery("Commentaire.findAllByProjet", Commentaire.class).setParameter("PROJET", projet).getResultList();
		return commentaires;
	}
}
