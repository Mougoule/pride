package fr.pride.project.services.business.exceptions;

import fr.pride.project.services.common.BusinessError;

/**
 * Business exception.
 * Should not be logged as it occurs in a normal application use case.
 * The error code must be precise enough to understand the error.
 * 
 *
 */
public class BusinessException extends BaseException {
	
	private static final long serialVersionUID = 7381608993108218227L;

	public BusinessException(BusinessError code, String message) {
		super(code.getCode(), message);
	}
	
	public BusinessException(BusinessError code, String message, String description) {
		super(code.getCode(), message, description);
	}
	
	@Override
	public boolean isGrave() {
		return false;
	}

}
