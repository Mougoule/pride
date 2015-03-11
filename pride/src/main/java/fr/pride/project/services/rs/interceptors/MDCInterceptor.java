package fr.pride.project.services.rs.interceptors;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pride.project.application.PrideMDC;

/**
 * Intercepteur ajoutant la méthode et le path aux logs
 * 
 *
 */
public class MDCInterceptor extends AbstractPhaseInterceptor<Message> {
	
	/** Logger */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MDCInterceptor.class);
	
	/**
	 * L'intercepteur est invoqué le plus tôt possible
	 */
	public MDCInterceptor() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		// On ajoute la méthode et le chemin de l'appel au MDC pour les logs
		PrideMDC.of((String) message.get(Message.HTTP_REQUEST_METHOD), (String) message.get(Message.PATH_INFO));
	}
	
}
