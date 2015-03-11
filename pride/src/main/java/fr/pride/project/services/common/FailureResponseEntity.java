package fr.pride.project.services.common;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Réponse KO pour les services restful
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FailureResponseEntity {

	/** code d'erreur */
	private final String code;
	
	/** message pour le client */
	private final String message;
	
	/** message technique */
	private final List<Object> errors = new LinkedList<Object>();

	public FailureResponseEntity() {
		this.code = "00";
		this.message = "Unknown error";
	}
	
	private FailureResponseEntity(String code, String message,
			List<Object> errors) {
		this.code = code;
		this.message = message;
		this.errors.addAll(errors);
	}

	/**
	 * Builder de la classe
	 * 
	 *
	 */
	public static class Builder {

		private String code;
		private String message;
		private final List<Object> errors = new LinkedList<Object>();

		/**
		 * Construction de la classe
		 * 
		 * @return nouvelle entitée
		 * @throws IllegalStateException en cas de données invalides
		 */
		public FailureResponseEntity build() {
			if (code == null) {
				throw new IllegalStateException("invalid code");
			}
			return new FailureResponseEntity(code, message, errors);
		}

		public Builder setCode(String code) {
			this.code = code;
			return this;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder addError(Object error) {
			this.errors.add(error);
			return this;
		}
		
		public Builder addAllError(List<Object> errors) {
			this.errors.addAll(errors);
			return this;
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}

	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public List<Object> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
