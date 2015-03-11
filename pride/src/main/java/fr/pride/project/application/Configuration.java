package fr.pride.project.application;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration de l'application. Permet d'accÈder aux propriÈtÈs du fichier de
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

	/** PropriÈtÈs chargÈes */
	private static final Properties PROPERTIES = initProperties();
	
	
	/* Constructeurs */

	/** Contructeur privÈ */
	private Configuration() {

	}

	/**
	 * Charge le properties
	 * 
	 * @return Le properties chargÈ
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

	/* M√©thodes publiques */

	/**
	 * Retourne une propri√©t√© pour la cl√© donn√©e.
	 * 
	 * @param configurationProperty La propri√©t√© cl√©
	 * @return La propri√©t√©
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
	 * La dur√©e de vie du token
	 * 
	 * @return La dur√©e de vie du token
	 */
	public static long getTokenDuration() {
		return Long.valueOf(get(ConfigurationProperty.APP_TOKEN_DURATION));
	}

}