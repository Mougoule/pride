package fr.pride.project.services.common;

/**
 * Comportement de base pour les beans dans lesquelles on a besoin de 
 * supporter le cycle de vie de <code>spring</code>
 * 
 *
 */
public interface LifeCycleRestService {
	
	/**
	 * après l'execution du constructeur 
	 */
	void init();
	
	/**
	 * juste avant l'elimination de l'instance
	 */
	void destroy();
}
