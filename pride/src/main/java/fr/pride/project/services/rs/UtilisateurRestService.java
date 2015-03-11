package fr.pride.project.services.rs;

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

import fr.pride.project.model.Utilisateur;
import fr.pride.project.services.business.UtilisateurBusinessService;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.rs.helpers.RestServiceHelper;

@Path("/utilisateurs")
public class UtilisateurRestService {

	@Autowired
	private UtilisateurBusinessService utilisateurBusinessService;

	/**
	 * Web service de r�cup�ration d'un utilisateur par son login.
	 * On utilise une requ�te HTTP avec la m�thode GET. Le login est pass� par l'URL
	 * 
	 * @param login le login de l'utilisateur
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
	 * Web service d'inscription d'un utilisateur.
	 * On utilise une requ�te HTTP avec la m�thode PSOT. Les donn�es sont pass�es par un FormParam.
	 * 
	 * @param login le login de l'utilisateur
	 * @param password son password
	 * @param email son email
	 * @param pseudo son pseudo
	 * @return true si cr��, une exception sinon (utilisateur non existant)
	 */
	@POST
	@Produces("application/json")
	@Path("")
	public Response inscrireUtilisateur(@FormParam("login") String login, @FormParam("password") String password,
			@FormParam("email") String email, @FormParam("pseudo") String pseudo) {

		Response response;
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setLogin(login);
		utilisateur.setPassword(password);
		utilisateur.setEmail(email);
		utilisateur.setPseudo(pseudo);

		try {
			utilisateurBusinessService.inscrireUtilisateur(utilisateur);
			response = RestServiceHelper.handleSuccessfulResponse(true);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}

	/**
	 * Web service de d�sincription d'un utilisateur gr�ce � son login.
	 * On utilise une requ�te HTTP avec la m�thode DELETE. Le login est pass� par un FormParam
	 * 
	 * @param login le login de l'utilisateur � supprimer
	 * @return true si supprim�, une exception sinon (utilisateur non existant)
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
	 * Web service de modification d'un utilisateur.
	 * On utilise une requ�te HTTP avec la m�thode PUT. Les donn�es sont pass�es par un FormParam
	 * 
	 * @param login le login de l'utilisateur � modifier
	 * @param password son password
	 * @param email son email
	 * @param pseudo son pseudo
	 * @return true si la modification est un succ�s, une exception sinon (utilisateur non existant)
	 */
	@PUT
	@Produces("application/json")
	@Path("")
	public Response modifierUtilisateur(@FormParam("login") String login, @FormParam("password") String password,
			@FormParam("email") String email, @FormParam("pseudo") String pseudo) {

		Response response;

		try {
			utilisateurBusinessService.modifierUtilisateur(login, password, email, pseudo);
			response = RestServiceHelper.handleSuccessfulResponse(true);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}
}
