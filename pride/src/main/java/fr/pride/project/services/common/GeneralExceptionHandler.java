package fr.pride.project.services.common;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.common.ResponseFactory.ResponseType;
import fr.pride.project.services.rs.helpers.RestServiceHelper;

/**
 * Handler gérant les exceptions des services dans les WS RS
 * 
 *
 */
public class GeneralExceptionHandler implements ExceptionMapper<Exception> {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GeneralExceptionHandler.class);

	/**
	 * @see ExceptionMapper#toResponse(Throwable)
	 */
	@Override
	public Response toResponse(Exception exception) {
		if (exception instanceof BaseException) {
			BaseException baseException = (BaseException) exception;
			Status status = null;
			if (baseException.getCode().equals(BaseError.ERROR_SECURITY_TOKEN.getCode())) {
				status = Status.UNAUTHORIZED;
			}
			return RestServiceHelper.handleFailureResponse((BaseException) exception, status);
		} else {
			LOGGER.error("Catching unknown error not handled in the RS layer", exception);

			FailureResponseEntity.Builder responseBuilder = (FailureResponseEntity.Builder) ResponseFactory
					.getResponseEntity(ResponseType.ERROR);
			responseBuilder
					.setCode(BaseError.ERROR_GENERIC.getCode())
					.addError(exception.getLocalizedMessage())
					.setMessage(exception.getMessage());

			return Response.serverError().entity(responseBuilder.build())
					.type(MediaType.APPLICATION_JSON).build();
		}
	}
}
