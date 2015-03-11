package fr.pride.project.services.business;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import fr.pride.project.model.Parameter;
import fr.pride.project.model.beans.ParameterKey;
import fr.pride.project.services.business.ApplicationBusinessService;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.business.exceptions.TechnicalException;
import fr.pride.project.services.common.CustomError;

/**
 * Implementation of {@link ApplicationBusinessService}
 * 
 *
 */
@EnableTransactionManagement
@Service
public class ApplicationBusinessService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationBusinessService.class);
	
	/** Cache des paramètres */
	private static final Cache<ParameterKey, Object> PARAMETERS_CACHE = CacheBuilder.newBuilder()
			.expireAfterWrite(1, TimeUnit.DAYS)
			.expireAfterAccess(1, TimeUnit.DAYS)
			.build();
	
	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Creates a new parameter
	 * 
	 * @param input Parameter to create
	 * @return The parameter
	 * @throws BaseException Shit happens
	 */
	@Transactional
	public Parameter addParameter(Parameter input) throws BaseException {
		LOGGER.info("Ajout d'un paramètre");
		Assert.notNull(input);
		em.persist(input);

		PARAMETERS_CACHE.put(input.getKey(), input.getValue());

		return input;
	}

	/**
	 * Gets a new parameter
	 * 
	 * @param key Parameter key
	 * @return The parameter value
	 * @throws BaseException Shit happens
	 */
	public Object getParameter(final ParameterKey key) throws BaseException {
		LOGGER.info("récupération d'un paramètre");
		
		Object value;
		try {
			value = PARAMETERS_CACHE.get(key, new Callable<Object>() {
				@Override
				public Object call() throws BaseException {
					Parameter response = em.find(Parameter.class, key);
					if (response == null) {
						throw new BusinessException(CustomError.ERROR_GENERIC_NOT_FOUND, 
								"Could not find parameter for key " + key);
					} else {
						return response.getValue();
					}
				}
			});
		} catch(ExecutionException e) {
			Throwable cause = e.getCause();
			if (cause instanceof BaseException) {
				throw (BaseException) cause;
			} else {
				throw new TechnicalException("An error occured while retrieving parameter for key " + key, e);
			}
		}
		return value;
	}

	/**
	 * Updates a new parameter
	 * 
	 * @param input Parameter to update
	 * @return The parameter updated
	 * @throws BaseException Shit happens
	 */
	@Transactional
	public Parameter updateParameter(ParameterKey key, String value) throws BaseException {
		LOGGER.info("modification d'un paramètre");

		Parameter response = em.find(Parameter.class, key);
		response.setValue(value);
		em.merge(response);

		return response;
	}

}
