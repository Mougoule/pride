package fr.pride.project.services.business;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pride.project.model.Equipe;
import fr.pride.project.model.Projet;
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
	private EquipeBusinessService equipeBusinessService;

	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;
	
	@Tokenized
	public Projet getProjetByNomProjet(String nomProjet){
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
		projet.setNoteProjet(0);
		em.persist(projet);
		
		Set<Equipe> equipe = new HashSet<Equipe>();
		equipe.add(equipeBusinessService.createEquipe(login, nomProjet));
		
		projet.setEquipes(equipe);
		
		em.merge(projet);
	}
}
