package fr.pride.project.application;

/**
 * Clés des propriétés de configuration l'application.
 * 
 *
 */
public enum ConfigurationProperty {
	
	APP_NAME("application.name"),
	APP_VERSION("application.version"),
	
	APP_TOKEN_DURATION("application.token.duration");
	
	/** Clé de a configuration */
	private String key;
	
	private ConfigurationProperty(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
}
