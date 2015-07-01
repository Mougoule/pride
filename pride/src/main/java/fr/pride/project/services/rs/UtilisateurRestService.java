package fr.pride.project.services.rs;

import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import fr.pride.project.model.Commentaire;
import fr.pride.project.model.CommentaireId;
import fr.pride.project.model.Projet;
import fr.pride.project.model.Utilisateur;
import fr.pride.project.model.beans.Connexion;
import fr.pride.project.services.business.CommentaireBusinessService;
import fr.pride.project.services.business.IdeeBusinessService;
import fr.pride.project.services.business.ProjetBusinessService;
import fr.pride.project.services.business.UtilisateurBusinessService;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.rs.helpers.RestServiceHelper;

@Path("/utilisateurs")
public class UtilisateurRestService {

	@Autowired
	private UtilisateurBusinessService utilisateurBusinessService;

	@Autowired
	private ProjetBusinessService projetBusinessService;

	@Autowired
	private IdeeBusinessService ideeBusinessService;

	@Autowired
	private CommentaireBusinessService commentaireBusinessService;

	/**
	 * Web service de récupération d'un utilisateur par son login. On utilise
	 * une requête HTTP avec la méthode GET. Le login est passé par l'URL
	 * 
	 * @param login
	 *            le login de l'utilisateur
	 * @return l'utilisateur ou null si rien
	 */
	@GET
	@Produces("application/json")
	@Path("/{login}")
	public Response getUtilisateurByLogin(@PathParam("login") String login) {

		Response response;
		Utilisateur utilisateur = utilisateurBusinessService.getUtilisateurByLogin(login);
		response = RestServiceHelper.handleSuccessfulResponse(utilisateur);
		return response;
	}

	/**
	 * Web service de connexion d'un utilisateur par son login et son mot de
	 * passe.
	 * 
	 * @param login
	 *            le login de l'utilisateur
	 * @param password
	 *            le password de l'utilisateur
	 * 
	 * @return l'utilisateur et le token ou null si rien
	 */
	@POST
	@Produces("application/json")
	@Path("/connect")
	public Response connexion(@FormParam("login") String login, @FormParam("password") String password) {

		Response response;
		Connexion bean;

		try {
			bean = utilisateurBusinessService.connexion(login, password);
			response = RestServiceHelper.handleSuccessfulResponse(bean);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}

		return response;
	}

	/**
	 * Web service d'ription d'un utilisateur. On utilise une requ�te HTTP avec
	 * la méthode POST. Les données sont passées par un FormParam.
	 * 
	 * @param login
	 *            le login de l'utilisateur
	 * @param password
	 *            son password
	 * @param email
	 *            son email
	 * @return utilisateur créé sinon erreur
	 */
	@POST
	@Produces("application/json")
	@Path("")
	public Response inscrireUtilisateur(@FormParam("login") String login, @FormParam("password") String password,
			@FormParam("email") String email) {

		Response response;
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setLogin(login);
		utilisateur.setPassword(password);
		utilisateur.setEmail(email);

		try {
			utilisateur = utilisateurBusinessService.inscrireUtilisateur(utilisateur);
			response = RestServiceHelper.handleSuccessfulResponse(utilisateur);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}

	/**
	 * Web service de désincription d'un utilisateur grâce à son login. On
	 * utilise une requéte HTTP avec la méthode DELETE. Le login est passé par
	 * un FormParam
	 * 
	 * @param login
	 *            le login de l'utilisateur à supprimer
	 * @return true si supprimé, une exception sinon (utilisateur non existant)
	 */
	@DELETE
	@Produces("application/json")
	@Path("")
	public Response desincrireUtilisateur(@FormParam("login") String login) {

		Response response;

		try {
			utilisateurBusinessService.desinscrireUtilisateur(login);
			response = RestServiceHelper.handleSuccessfulResponse(true);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}

	/**
	 * Web service de modification d'un utilisateur. On utilise une requête HTTP
	 * avec la méthode PUT. Les données sont passées par un FormParam
	 * 
	 * @param login
	 *            le login de l'utilisateur à modifier
	 * @param password
	 *            son password
	 * @param email
	 *            son email
	 * @return true si la modification est un succés, une exception sinon
	 *         (utilisateur non existant)
	 */
	@PUT
	@Produces("application/json")
	@Path("")
	public Response modifierUtilisateur(@FormParam("login") String login, @FormParam("password") String password,
			@FormParam("email") String email) {

		Response response;

		try {
			utilisateurBusinessService.modifierUtilisateur(login, password, email);
			response = RestServiceHelper.handleSuccessfulResponse(true);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}

	/**
	 * Web service pour commenter On utilise une requête HTTP avec la méthode
	 * POST. Les données sont passées par un FormParam.
	 * 
	 * @param login
	 *            le login de l'utilisateur
	 * @param nomProjet
	 *            le nom du projet à commenter
	 * @param texte
	 *            le contenu du commentaire
	 * @return true si créé, une exception sinon
	 */
	@POST
	@Produces("application/json")
	@Path("/commenter")
	public Response commenter(@FormParam("login") String login, @FormParam("nomProjet") String nomProjet,
			@FormParam("texte") String texte) {

		Response response;

		CommentaireId commentaireId = new CommentaireId();
		commentaireId.setIdProjet(nomProjet);
		commentaireId.setIdUtilisateur(login);

		Commentaire commentaire = new Commentaire();
		commentaire.setId(commentaireId);
		commentaire.setTexte(texte);
		commentaire.getId().setDate(new Date());
		commentaire.setProjet(projetBusinessService.getProjetByNomProjet(nomProjet));
		commentaire.setUtilisateur(utilisateurBusinessService.getUtilisateurByLogin(login));

		try {
			commentaireBusinessService.creerCommentaire(commentaire);
			response = RestServiceHelper.handleSuccessfulResponse(true);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}

	/**
	 * Web service pour récupérer les projets d'un utilisateur
	 * 
	 * @param login
	 *            le login de l'utilisateur
	 * @return les projets, une exception sinon
	 */
	@GET
	@Produces("application/json")
	@Path("/projet/{login}")
	public Response getProjetByLogin(@PathParam("login") String login) {

		Response response;
		List<Projet> projets;

		try {
			projets = utilisateurBusinessService.getProjets(login);
			response = RestServiceHelper.handleSuccessfulResponse(projets);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}

	/**
	 * Création d'une idée pour un projet
	 * @param login le login de l'utilisateur créant l'idée
	 * @param nomProjet le nom du projet
	 * @param ideeString l'idée
	 */
	@POST
	@Produces("application/json")
	@Path("/projet/idee")
	public Response createIdeeForProjet(@FormParam("login") String login, @FormParam("nomProjet") String nomProjet,
			@FormParam("idee") String ideeString) {

		Response response;

		try {
			ideeBusinessService.createIdeeForProjet(login, nomProjet, ideeString);
			response = RestServiceHelper.handleSuccessfulResponse(null);
		} catch (BusinessException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}

		return response;
	}

	/**
	 * Récupère les utilisateurs qui ne sont pas associer au projet
	 * @param nomProjet
	 * @return
	 */
	@GET
	@Produces("application/json")
	@Path("/notin/projet/{nomUtilisateur}")
	public Response getUtilisateurNotInProjet(@PathParam("nomProjet") String nomProjet) {

		Response response;
		List<Utilisateur> utilisateurs;

		utilisateurs = utilisateurBusinessService.getUtilisateurNotInProjet(nomProjet);
		response = RestServiceHelper.handleSuccessfulResponse(utilisateurs);
		return response;
	}
}
