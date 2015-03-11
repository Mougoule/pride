package fr.pride.project.application;

/**
 * Cl�s des propri�t�s de configuration l'application.
 * 
 *
 */
public enum ConfigurationProperty {
	
	APP_NAME("application.name"),
	APP_VERSION("application.version"),
	
	APP_TOKEN_DURATION("application.token.duration");
	
	/** Cl� de a configuration */
	private String key;
	
	private ConfigurationProperty(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
}
