package fr.pride.project.services.rs;

/**
 * Base pour les services rest
 *
 */
public abstract class BaseRestService {
	
	/**
	 * calcule de la dernier page dns le procesus de pagination
	 * @param pageSize taille de la page
	 * @param countResult quantité d'elements dans la base
	 * @return index de la dernier page
	 */
	protected int getLastPageNumber(int pageSize, int countResult) {
		double tempValue = (countResult / (double) pageSize);
		int lastPageNumber = (int) (tempValue);
		if (tempValue > lastPageNumber) {
			return lastPageNumber + 1;
		}
		
		return lastPageNumber;
	}
    	
}
