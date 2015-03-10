package fr.pride.project.application;

import org.slf4j.MDC;

/**
 * Classe utilitaire du MDC de l application
 * 
 */
public final class PrideMDC {

	private static final String REQUESTOR = "requestor";
	private static final String RESOURCE = "resource";

	private PrideMDC() {
	}

	/**
	 * Initialise le MDC courant avec les param�tres. 
	 * 
	 * @param requestor
	 *            l'initiateur user dans le contexte web, trigger dans les
	 *            batchs
	 * @param resource
	 *            la resource implique l'uri coté web, le job coté batch
	 */
	public static void of(final String requestor, final String resource) {
		MDC.put(REQUESTOR, requestor);
		MDC.put(RESOURCE, resource);
	}

}
