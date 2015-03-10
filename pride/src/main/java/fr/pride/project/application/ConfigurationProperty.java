package fr.pride.project.application;

/**
 * Clés des propriétés de configuration l'application.
 * 
 *
 */
public enum ConfigurationProperty {
	
	APP_NAME("application.name"),
	APP_VERSION("application.version"),
	
	APP_TOKEN_DURATION("application.token.duration"),
	
	APP_MAIL_FROM_EMAIL("application.mail.from.email"),
	APP_MAIL_FROM_NAME("application.mail.from.name"),
	APP_MAIL_ENCODING("application.mail.encoding");
	
	/** Clé de a configuration */
	private String key;
	
	private ConfigurationProperty(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
}
