package fr.pride.project.services.common;

/**
 * Liste d'erreurs métier
 * 
 *
 */
public enum CustomError implements BusinessError {
	
	/* Erreurs génériques */
	ERROR_GENERIC_NOT_FOUND("GEN_01"),
	
	/* Erreurs de securité */
	ERROR_SECURITY_INVALID_CREDENTIALS("SEC_02"),
	
	/* Erreurs du client */
	ERROR_CUSTOMER_GENERIC("CLI_00"),
	ERROR_CUSTOMER_INVALID_DATA("CLI_01"),
	ERROR_CUSTOMER_ALREADY_EXISTS("CLI_02");

	/** Code de l'erreur */
	final String code;

	CustomError(String code) {
		this.code = code;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
}