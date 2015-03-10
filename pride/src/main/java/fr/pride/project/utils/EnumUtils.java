package fr.pride.project.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public final class EnumUtils {

	private static final String ENUM_VALUES_FIELD_1 = "ENUM$VALUES";
	private static final String ENUM_VALUES_FIELD_2 = "$VALUES";

	private EnumUtils() {
		
	}
	
	public static <T extends Enum<T>> T valueOf(Class<T> clazz, String name) {
		T result = null;
		if (StringUtils.isNotEmpty(name)) {
			try {
				result = Enum.valueOf(clazz, name);
			} catch (IllegalArgumentException e) {
				// Valeur non trouvée
			}
		}
		return result;
	}

	/**
	 * Convertit une enum en Map contenant les champs de l'enum.
	 * 
	 * XXX Cette fonction utiise la réflection donc les performances sont
	 * mauvaises. Elle ne doit pas être utlisée dans une application en
	 * production.
	 * 
	 * @param enumType Classe de l'enum
	 * @return Une Map contenant les différents champs de l'enum
	 */
	public static <E extends Enum<E>> Map<String, Map<String, Object>> toMap(Class<E> enumType) {
		E[] constants = enumType.getEnumConstants();
		Map<String, Map<String, Object>> toReturn = new LinkedHashMap<String, Map<String, Object>>(constants.length);

		// Parcours des différentes constantes de l'enum
		for (E c : constants) {
			String name = c.name();
			Field[] fields = enumType.getDeclaredFields();
			Map<String, Object> values = new HashMap<String, Object>(fields.length);
			// Parcours des différents champs
			for (Field f : fields) {
				String fieldName = f.getName();
				if (!f.isEnumConstant() && !ENUM_VALUES_FIELD_1.equals(fieldName) && !ENUM_VALUES_FIELD_2.equals(fieldName)) {
					f.setAccessible(true);
					try {
						Object value = f.get(c);
						values.put(fieldName, value);
					} catch (IllegalArgumentException e) {
						values.put(fieldName, "IllegalArgumentException");
					} catch (IllegalAccessException e) {
						values.put(fieldName, "IllegalAccessException");
					}
				}
			}
			toReturn.put(name, values);
		}

		return toReturn;
	}

}
