package fr.pride.project.services.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.pride.project.model.Parameter;
import fr.pride.project.model.beans.ParameterKey;
import fr.pride.project.services.business.ApplicationBusinessService;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.rs.helpers.RestServiceHelper;

@Path("/parameters")
public class ParameterRestService {

	/** Logger */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ParameterRestService.class);

	@Autowired
	private ApplicationBusinessService applicationBusinessService;

	/**
	 * Récupération paramètre
	 * @return Le paramètre
	 */
    @GET
    @Path("/{key}")
    @Produces("application/json")
    public Response getParameter(@PathParam("key") String keyStr) {
		Response response;

		ParameterKey key = Enum.valueOf(ParameterKey.class, keyStr);

		try {
			Object param = applicationBusinessService.getParameter(key);
			response = RestServiceHelper.handleSuccessfulResponse(param);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}

		return response;
	}

    /**
     * Adds a new parameter.
     * Only for test purposes.
     * @param keyStr Key
     * @return Parameter created
     */
    @POST
    @Produces("application/json")
    @Path("/{key}")
    public Response addParameter(@PathParam("key") String keyStr) {
		Response response;
		try {
			ParameterKey key = Enum.valueOf(ParameterKey.class, keyStr);
			Parameter param = new Parameter();

			param.setKey(key);
			String val = "MICKEY";
			param.setValue(val);
			param = applicationBusinessService.addParameter(param);

			response = RestServiceHelper.handleSuccessfulResponse(param);
		} catch (BaseException e) {
			response = RestServiceHelper.handleFailureResponse(e);
		}

		return response;
	}

}
