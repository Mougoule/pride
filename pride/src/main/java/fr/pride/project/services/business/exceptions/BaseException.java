package fr.pride.project.services.business.exceptions;

import java.io.Serializable;

/**
 * Basic exception
 */
public abstract class BaseException extends Exception implements Serializable {

	private static final long serialVersionUID = -3847419422552854689L;
	
	/** Error code */
	private String code;
	
	/** Error description */
	private String description;
	
	public BaseException(String code, String message, String description) {
		super(message);
		this.code = code;
		this.description = description;
	}
	
	public BaseException(String code, String message) {
		super(message);
		this.code = code;
		this.description = message;
	}
	
	/**
	 * @return if exception is grave
	 */
	public abstract boolean isGrave();
	
	/**
     * @return code d'exception
     */
	public String getCode() {
		return code;
	}

    /**
     * @return description technique du problème
     */
	public String getDescription() {
		return description;
	}

}
