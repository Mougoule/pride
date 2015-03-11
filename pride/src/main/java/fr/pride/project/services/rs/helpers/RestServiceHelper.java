package fr.pride.project.services.rs.helpers;

import java.lang.reflect.Method;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.message.Message;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.common.FailureResponseEntity;
import fr.pride.project.services.common.ResponseFactory;
import fr.pride.project.services.common.SuccessfulResponseEntity;
import fr.pride.project.services.common.ResponseFactory.ResponseType;

/**
 * Helper pour génrérer les réponses des WS REST
 * 
 *
 */
public final class RestServiceHelper {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceHelper.class);
	
	/**
	 * Retourne une réponse avec un seule objet
	 * 
	 * @param object
	 *            objet à  ajouter dans la réponse
	 * @return envelope de réponse
	 */
	public static Response handleSuccessfulResponse(Object object) {
		SuccessfulResponseEntity.Builder responseEntityBuilder = (SuccessfulResponseEntity.Builder) ResponseFactory
				.getResponseEntity(ResponseType.SUCESS);
		SuccessfulResponseEntity responseEntity = responseEntityBuilder.setData(object).setSuccess(true).build();
		return Response.ok().entity(responseEntity).build();
	}

	/**
	 * Retourne une réponse erronée pour une exception en particulier
	 * 
	 * @param input
	 *            erreur de base
	 * @return enveloper de réponse
	 */
	public static Response handleFailureResponse(BaseException input) {
		return handleFailureResponse(input, null);
	}

	/**
	 * Retourne une réponse erronée pour une exception en particulier
	 * 
	 * @param input
	 *            erreur de base
	 * @return enveloper de réponse
	 */
	public static Response handleFailureResponse(BaseException input, Status status) {
		ResponseBuilder responseBuilder;

		FailureResponseEntity.Builder builder = (FailureResponseEntity.Builder) ResponseFactory
				.getResponseEntity(ResponseType.ERROR);

		builder.setCode(input.getCode()).addError(input.getMessage()).setMessage(input.getDescription());

		if (status == null) {
			if (input.isGrave()) {
				// Erreur technique imprévue, on loggue
				LOGGER.error("Une erreur s'est produite dans le web service", input);
				responseBuilder = Response.serverError();
			} else {
				// Erreur fonctionnelle, on loggue pas et on considère que c'est
				// un cas normal
				responseBuilder = Response.ok();
			}
		} else {
			responseBuilder = Response.status(status);
		}

		responseBuilder.entity(builder.build());

		return responseBuilder.build();
	}
	
	/**
	 * Retrouve la méthode Java appelée par un service RS
	 * 
	 * @param message Le message
	 * @return La méthode correspondante
	 */
	public static Method findServiceMethod(Message message) {
        Message inMessage = message.getExchange().getInMessage();
        Method method = (Method)inMessage.get("org.apache.cxf.resource.method");
        if (method == null) {
            BindingOperationInfo bop = inMessage.getExchange().get(BindingOperationInfo.class);
            if (bop != null) {
                MethodDispatcher md = (MethodDispatcher) 
                    inMessage.getExchange().get(Service.class).get(MethodDispatcher.class.getName());
                method = md.getMethod(bop);
            }
        }
        return method;
    }

}
