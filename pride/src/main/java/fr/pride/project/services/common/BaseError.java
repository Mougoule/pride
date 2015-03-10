package fr.pride.project.services.common;

/**
 * Erreur de base traitées par le Exception Handler
 * 
 *
 */
public enum BaseError implements BusinessError {
	
	/* Erreurs génériques */
	ERROR_GENERIC("GEN_00"),
	
	/* Erreurs de securité */
	ERROR_SECURITY_TOKEN("SEC_00");
	
	/** Code de l'erreur */
	final String code;

	BaseError(String code) {
		this.code = code;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
}