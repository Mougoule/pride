package fr.pride.project.services.business.exceptions;

/**
 * Describes an unexpected error during service execution.
 * This exception should be logged when caught.
 * Cause is mandatory unless it as none, in this case, put <i>null</i>.
 * 
 *
 */
public class TechnicalException extends BaseException {

	private static final long serialVersionUID = -2915341675551468815L;

	/** Code de base des erreurs techniques */
	public static final String TECHNICAL_CODE = "TECH";
	
	public TechnicalException(String message, Throwable cause) {
		super(TECHNICAL_CODE, message);
		this.initCause(cause);
	}
	
	public TechnicalException(String message, String description, Throwable cause) {
		super(TECHNICAL_CODE, message, description);
		this.initCause(cause);
	}
	
	@Override
	public boolean isGrave() {
		return true;
	}

}
