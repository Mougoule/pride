package fr.pride.project.services.business;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pride.project.model.Utilisateur;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.common.CustomError;

@Service
public class UtilisateurBusinessService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurBusinessService.class);

	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;

	/**
	 * R�cup�re un utilisateur par son login
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @return l'utilisateur ou null si rien
	 */
	public Utilisateur getUtilisateurByLogin(String login) {
		LOGGER.info("R�cup�ration d'un utilisateur par son login : {}", login);
		try {
			return em.createNamedQuery("Utilisateur.findByLogin", Utilisateur.class).setParameter("LOGIN", login)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Permet l'inscription d'un utilisateur
	 * 
	 * @param utilisateur
	 *            l'utilisateur � inscrire
	 * @throws BaseException
	 *             si le login est d�j� utilis�
	 */
	@Transactional
	public void inscrireUtilisateur(Utilisateur utilisateur) throws BaseException {
		String login = utilisateur.getLogin();
		Utilisateur response = getUtilisateurByLogin(login);
		LOGGER.info("Inscription de l'utilisateur : {}", login);
		if (response != null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_ALREADY_EXISTS,
					"Impossible de cr�er l'utilisateur, ce login est d�j� utilis� : " + login);
		}
		em.persist(utilisateur);
	}

	/**
	 * Permet de d�sinscrire un utilisateur gr�ce � son login
	 * 
	 * @param login
	 *            le login de l'utilisateur � d�sincrire (suppression
	 * @throws BaseException
	 *             si l'utilisateur n'est pas trouv�
	 */
	@Transactional
	public void desinscrireUtilisateur(String login) throws BaseException {
		LOGGER.info("D�sinscription de l'utilisateur : {}", login);
		Utilisateur utilisateur = getUtilisateurByLogin(login);
		if (utilisateur == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_NOT_FOUND,
					"Erreur lors de la suppression, aucun utilisateur pour le login : " + login);
		}
		em.remove(utilisateur);
	}

	/**
	 * Permet la modification d'un utilisateur trouv� par son login (seul le
	 * password, l'email et le pseudo peuvent �tre modifier)
	 * 
	 * @param login
	 *            le login de l'utilisateur
	 * @param password
	 *            le nouveau password (ou l'ancien)
	 * @param email
	 *            le nouvel email (ou l'ancien)
	 * @param pseudo
	 *            le nouveau pseudo (ou l'ancien
	 * @throws BaseException
	 *             si l'utilisateur n'a pas �t� trouv�
	 */
	@Transactional
	public void modifierUtilisateur(String login, String password, String email, String pseudo) throws BaseException {

		LOGGER.info("Inscription de l'utilisateur : {}", login);
		Utilisateur ancienUtilisateur = getUtilisateurByLogin(login);
		if (ancienUtilisateur == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_NOT_FOUND,
					"Erreur lors de la modification, aucun utilisateur pour le login : " + login);
		}

		ancienUtilisateur.setEmail(email);
		ancienUtilisateur.setPseudo(pseudo);
		ancienUtilisateur.setPassword(password);
		em.merge(ancienUtilisateur);
	}
}
