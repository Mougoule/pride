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
 * Intercepteur permettant de vérifiant le token.
 * Le token doit Ãªtre placé dans le header.
 * 
 *
 */
public class TokenInterceptor extends AbstractPhaseInterceptor<Message> {
	
	/** Nom du paramètre du header contenant le token */
	public static final String TOKEN_PARAM_NAME = "token";
	
	/** Logger */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TokenInterceptor.class);
	
	/** Service de sécurité */
	@Autowired
	private SecurityBusinessService securityBusinessService;
	

	/**
	 * L'intercepteur est invoqué dès qu'il est possible d'obtenir la méthode Java appelée
	 */
	public TokenInterceptor() {
		super(Phase.PRE_INVOKE);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void handleMessage(Message message) throws Fault {
		// Récupération de la méthode appelée
		Method method = RestServiceHelper.findServiceMethod(message);
		
		// Vérification du caractère tokenizé de la méthode
		Tokenized tokenized = AnnotationUtils.findAnnotation(method, Tokenized.class);
		if (tokenized != null) {
			// La méthode nécessite un token
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
						// Vérification de la validité du token
						securityBusinessService.checkToken(token);
					} catch(BaseException e) {
						// Token expiré
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
	 * Lance un exception de sécurité liée au token
	 */
	private static void throwSecurityTokenException() {
		BusinessException e = new BusinessException(
				BaseError.ERROR_SECURITY_TOKEN, 
				"No token provided", 
				"You tried to access a tokenized method but you did not provide any token");
		throw new Fault(e);
	}
}
