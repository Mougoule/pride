package fr.pride.project.services.common;

/**
 * Erreur de base trait�es par le Exception Handler
 * 
 *
 */
public enum BaseError implements BusinessError {
	
	/* Erreurs g�n�riques */
	ERROR_GENERIC("GEN_00"),
	
	/* Erreurs de securit� */
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