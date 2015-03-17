package fr.pride.project.services.business;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.pride.project.model.Utilisateur;
import fr.pride.project.model.beans.Connexion;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.business.exceptions.TechnicalException;
import fr.pride.project.services.common.CustomError;
import fr.pride.project.services.utils.PasswordUtils;

@Service
public class UtilisateurBusinessService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurBusinessService.class);

	@Autowired
	private SecurityBusinessService securityBusinessService;

	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Récupère un utilisateur par son login
	 * 
	 * @param login
	 *            login de l'utilisateur
	 * @return l'utilisateur ou null si rien
	 */
	public Utilisateur getUtilisateurByLogin(String login) {
		LOGGER.info("Récupération d'un utilisateur par son login : {}", login);
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
	 *            l'utilisateur à inscrire
	 * @throws BaseException
	 *             si le login est déjà utilisé
	 */
	@Transactional
	public void inscrireUtilisateur(Utilisateur utilisateur) throws BaseException {
		String login = utilisateur.getLogin();
		Utilisateur response = getUtilisateurByLogin(login);
		if (response != null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_ALREADY_EXISTS,
					"Impossible de créer l'utilisateur, ce login est déjà utilisé : " + login);
		}
		LOGGER.info("Inscription de l'utilisateur : {}", login);
		
		try {
			String mdpHash = PasswordUtils.createHash(utilisateur.getPassword());
			LOGGER.debug("Mot de passe hashé : {}", mdpHash);
			utilisateur.setPassword(mdpHash);
			em.persist(utilisateur);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new TechnicalException("Impossible de créer l'utilisateur", "Impossible de hasher le mot de passe", e);
		}
	}

	/**
	 * Permet de désinscrire un utilisateur grâce à son login
	 * 
	 * @param login
	 *            le login de l'utilisateur à désincrire (suppression
	 * @throws BaseException
	 *             si l'utilisateur n'est pas trouvé
	 */
	@Transactional
	public void desinscrireUtilisateur(String login) throws BaseException {
		LOGGER.info("Désinscription de l'utilisateur : {}", login);
		Utilisateur utilisateur = getUtilisateurByLogin(login);
		if (utilisateur == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_NOT_FOUND,
					"Erreur lors de la suppression, aucun utilisateur pour le login : " + login);
		}
		em.remove(utilisateur);
	}

	/**
	 * Permet la modification d'un utilisateur trouvé par son login (seul le
	 * password, l'email et le pseudo peuvent être modifier)
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
	 *             si l'utilisateur n'a pas été trouvé
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

	public Connexion connexion(String login, String password) throws BaseException {
		LOGGER.info("Connexion de l'utilisateur : {}", login);
		Utilisateur utilisateur;

		utilisateur = getUtilisateurByLogin(login);

		if (utilisateur == null) {
			throw new BusinessException(CustomError.ERROR_UTILISATEUR_CONNEXION, "Login ou mot de passe incorrect");
		}

		try {
			// Vérification du mot de passe
			if (!PasswordUtils.validatePassword(password, utilisateur.getPassword())) {
				// Mot de passe incorrect
				throw new BusinessException(CustomError.ERROR_SECURITY_INVALID_CREDENTIALS,
						"Login ou mot de passe incorrect");
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// Erreur lors du hash du password
			throw new TechnicalException("Erreur lors de la connexion de l'utilisateur",
					"Impossible de hasher le mot de passe", e);
		}

		// Authentification OK, on récupère un nouveau token
		String token = securityBusinessService.newToken();

		Connexion connexion = new Connexion();
		connexion.setUtilisateur(utilisateur);
		connexion.setToken(token);

		return connexion;
	}
}
