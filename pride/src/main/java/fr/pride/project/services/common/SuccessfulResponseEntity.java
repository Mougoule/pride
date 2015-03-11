package fr.pride.project.services.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Réponse OK pour les services <code>restful</code>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SuccessfulResponseEntity {

	/** si l'operation a été correcte */
	private final boolean success;
	
	/** ontenu de la  réponse */
	private final Object data;

	private SuccessfulResponseEntity(boolean success, Object input) {
		this.success = success;
		this.data = input;
	}

	/**
	 * Builder de la classe
	 *
	 */
	public static class Builder {
		private boolean success;
		private Object data;

		/**
		 * Construction de la classe
		 * @return nouvelle entitée
		 */
		public SuccessfulResponseEntity build() {
			return new SuccessfulResponseEntity(this.success, this.data);
		}

		public Builder setSuccess(boolean success) {
			this.success = success;
			return this;
		}

		public Builder setData(Object data) {
			this.data = data;
			return this;
		}

	}

	public boolean isSuccess() {
		return success;
	}

	public Object getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
