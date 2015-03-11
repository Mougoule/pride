package fr.pride.project.services.common;

/**
 * Fabrique de réponse
 * 
 *
 */
public final class ResponseFactory {

	
	public static enum ResponseType {
		SUCESS, ERROR
	}
	
	public static Object getResponseEntity(ResponseType type) {
		switch (type) {
		case SUCESS:
			return new SuccessfulResponseEntity.Builder();
		case ERROR:
			return new FailureResponseEntity.Builder();
		}
		return null;
	}
	
}
