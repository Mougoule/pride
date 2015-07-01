package fr.pride.project.services.rs;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.pride.project.services.business.SecurityBusinessService;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.rs.helpers.RestServiceHelper;

@Path("/security")
public class SecurityRestService {

	/** Logger */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityRestService.class);

	/** Service de sécurité */
	@Autowired
	private SecurityBusinessService securityBusinessService;

	/**
	 * Retourne un nouveau token
	 * 
	 * @param login Login de l'utilisateur
	 * @param password Mot de passe de l'utilisateur 
	 * @return Le token
	 */
	@POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getNewToken() {
		Response response;
		try {
			String token = securityBusinessService.newToken();
			return RestServiceHelper.handleSuccessfulResponse(token);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}
		return response;
	}
	
}
