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
	
	/* Erreurs de l'utilisateur */
	ERROR_UTILISATEUR_GENERIC("UTI_00"),
	ERROR_UTILISATEUR_NOT_FOUND("UTI_01"),
	ERROR_UTILISATEUR_ALREADY_EXISTS("UTI_02"),
	
	/* Erreurs des projets */
	ERROR_PROJET_GENERIC("PRO_00"),
	ERROR_PROJET_NOT_FOUND("PRO_01"),
	ERROR_PROJET_ALREADY_EXIST("PRO_02"),
	
	/* Erreurs des collaborateurs */
	ERROR_COLLABORATEUR_GENERIC("COL_00"),
	ERROR_COLLABORATEUR_NOT_FOUND("COL_01"),
	ERROR_COLLABORATEUR_ALREADY_EXIST("COL_02"), 
	
	/* Erreurs des rôles*/
	ERROR_ROLE_GENERIC("ROL_00"),
	ERROR_ROLE_DOES_NOT_EXIST("ROL_01"),
	;

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