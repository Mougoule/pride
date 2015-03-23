package fr.pride.project.services.rs;

import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import fr.pride.project.model.Commentaire;
import fr.pride.project.model.Projet;
import fr.pride.project.services.business.CommentaireBusinessService;
import fr.pride.project.services.business.ProjetBusinessService;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.rs.helpers.RestServiceHelper;

@Path("/projets")
public class ProjetRestService {

	@Autowired
	private ProjetBusinessService projetBusinessService;
	
	@Autowired
	private CommentaireBusinessService commentaireBusinessService;
	
	@GET
	@Produces("application/json")
	@Path("/{nomProjet}")
	public Response getProjetByNom(@PathParam("nomProjet") String nomProjet) {

		Response response;
		Projet projet = projetBusinessService.getProjetByNomProjet(nomProjet);
		response = RestServiceHelper.handleSuccessfulResponse(projet);
		return response;
	}
	
	@GET
	@Produces("application/json")
	@Path("/commentaire/{nomProjet}")
	public Response getCommentaire(@PathParam("nomProjet") String nomProjet) {

		Projet projet = projetBusinessService.getProjetByNomProjet(nomProjet);
		
		Response response;
		Set<Commentaire> commentaires = projet.getCommentaires();
		response = RestServiceHelper.handleSuccessfulResponse(commentaires);
		return response;
	}

	@POST
	@Produces("application/json")
	@Path("")
	public Response creerProjet(@FormParam("login") String login, @FormParam("nomProjet") String nomProjet, @FormParam("description") String description) {

		Response response;
		try {
			projetBusinessService.creerProjet(login, nomProjet, description);
			response = RestServiceHelper.handleSuccessfulResponse(null);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}
}
