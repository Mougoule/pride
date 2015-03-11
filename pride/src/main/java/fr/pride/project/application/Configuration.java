package fr.pride.project.application;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration de l'application. Permet d'acc�der aux propri�t�s du fichier de
 * configuration pride.properties.
 * 
 */
public final class Configuration {

	// yo
	/** Logger */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	/** Fichier de configuration */
	private static final String APP_CONFIGURATION_PATH = "pride.properties";

	/** Propri�t�s charg�es */
	private static final Properties PROPERTIES = initProperties();
	
	
	/* Constructeurs */

	/** Contructeur priv� */
	private Configuration() {

	}

	/**
	 * Charge le properties
	 * 
	 * @return Le properties charg�
	 */
	private static Properties initProperties() {
		Properties properties = null;
		try {
			properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(APP_CONFIGURATION_PATH));
		} catch (IOException e) {
			throw new IllegalStateException("Error loading " + APP_CONFIGURATION_PATH, e);
		}
		return properties;
	}

	/* Méthodes publiques */

	/**
	 * Retourne une propriété pour la clé donnée.
	 * 
	 * @param configurationProperty La propriété clé
	 * @return La propriété
	 */
	public static String get(ConfigurationProperty configurationProperty) {
		return PROPERTIES.getProperty(configurationProperty.getKey());
	}

	/**
	 * Retourne le nom de l'application
	 * 
	 * @return Le nom de l'application
	 */
	public static String getApplicationName() {
		return get(ConfigurationProperty.APP_NAME);
	}

	/**
	 * La durée de vie du token
	 * 
	 * @return La durée de vie du token
	 */
	public static long getTokenDuration() {
		return Long.valueOf(get(ConfigurationProperty.APP_TOKEN_DURATION));
	}

}