package fr.pride.project.services.rs.interceptors;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import fr.pride.project.services.business.SecurityBusinessService;
import fr.pride.project.services.business.exceptions.BaseException;
import fr.pride.project.services.business.exceptions.BusinessException;
import fr.pride.project.services.common.BaseError;
import fr.pride.project.services.rs.annotations.Tokenized;
import fr.pride.project.services.rs.helpers.RestServiceHelper;

/**
 * Intercepteur permettant de v�rifiant le token.
 * Le token doit être plac� dans le header.
 * 
 *
 */
public class TokenInterceptor extends AbstractPhaseInterceptor<Message> {
	
	/** Nom du param�tre du header contenant le token */
	public static final String TOKEN_PARAM_NAME = "token";
	
	/** Logger */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TokenInterceptor.class);
	
	/** Service de s�curit� */
	@Autowired
	private SecurityBusinessService securityBusinessService;
	

	/**
	 * L'intercepteur est invoqu� d�s qu'il est possible d'obtenir la m�thode Java appel�e
	 */
	public TokenInterceptor() {
		super(Phase.PRE_INVOKE);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void handleMessage(Message message) throws Fault {
		// R�cup�ration de la m�thode appel�e
		Method method = RestServiceHelper.findServiceMethod(message);
		
		// V�rification du caract�re tokeniz� de la m�thode
		Tokenized tokenized = AnnotationUtils.findAnnotation(method, Tokenized.class);
		if (tokenized != null) {
			// La m�thode n�cessite un token
			Map<String, Object> headers = (Map<String, Object>) message.get(Message.PROTOCOL_HEADERS);
			Object tokenObj = headers.get(TOKEN_PARAM_NAME);
			if (tokenObj != null && tokenObj instanceof List) {
				List<String> tokenList = (List<String>) tokenObj;
				if (tokenList.isEmpty()) {
					// Pas de token fourni dans l'appel
					throwSecurityTokenException();
				} else {
					// Token fourni
					String token = tokenList.get(0);
					try {
						// V�rification de la validit� du token
						securityBusinessService.checkToken(token);
					} catch(BaseException e) {
						// Token expir�
						throw new Fault(e);
					}
				}
				
			} else {
				// Pas de token fourni dans l'appel
				throwSecurityTokenException();
			}
		}
	}
	
	
	/**
	 * Lance un exception de s�curit� li�e au token
	 */
	private static void throwSecurityTokenException() {
		BusinessException e = new BusinessException(
				BaseError.ERROR_SECURITY_TOKEN, 
				"No token provided", 
				"You tried to access a tokenized method but you did not provide any token");
		throw new Fault(e);
	}
}
