package fr.pride.project.services.business;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import fr.pride.project.application.Configuration;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.common.BaseError;

/**
 * Implementation of {@link SecurityBusinessService}
 * 
 * Token functionalities cannot be used in a clustered environment
 * 
 *
 */
@Service
public class SecurityBusinessService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SecurityBusinessService.class);
	
	/** Durée de validité du token en minutes */
	private static final long TOKEN_DURATION = Configuration.getTokenDuration();
	
	/** Cache des tokens */
	private static final Cache<String, UUID> TOKEN_CACHE = CacheBuilder.newBuilder()
			.expireAfterWrite(TOKEN_DURATION, TimeUnit.MINUTES)
			.expireAfterAccess(TOKEN_DURATION, TimeUnit.MINUTES)
			.build();
	
	/** Entity Manager */
	@PersistenceContext
	private EntityManager em;

	public void checkToken(String token) throws BaseException {
		UUID uuid = TOKEN_CACHE.getIfPresent(token);
		if (uuid == null) {
			throw new BusinessException(
					BaseError.ERROR_SECURITY_TOKEN, 
					"Token not found or expired", 
					"You tried to access a tokenized method but your token (" + token + ") was not valid anymore");
		}
	}

	public String newToken() throws BaseException {
		LOGGER.info("Génération d'un nouveau token");
		
		// Credentials OK, génération d'un nouveau token
		UUID uuid = UUID.randomUUID();
		String token = uuid.toString();
		TOKEN_CACHE.put(token, uuid);
		return token;
	}

}
