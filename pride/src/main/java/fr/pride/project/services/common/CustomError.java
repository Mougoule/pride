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
	
	/* Erreurs de l'utilisaeur */
	ERROR_UTILISATEUR_GENERIC("UTI_00"),
	ERROR_UTILISATEUR_NOT_FOUND("UTI_01"),
	ERROR_UTILISATEUR_ALREADY_EXISTS("UTI_02");

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