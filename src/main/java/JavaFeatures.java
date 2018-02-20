import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Просто различные приложения по поведению стандартных Java классов
 */
@Log4j2
public class JavaFeatures {

	public static void main(String[] args) {
		featureObjects();
	}

	/**
	 * Objects - утилитный класс, который добавляется такие штуки:
	 */
	private static void featureObjects() {
		String s1 = null;
		String s2 = "";

		// Вычисление hashCode, в том числе и от null без NPE
		log.debug(Objects.hashCode(s1));
		log.debug(Objects.hashCode(s2));

		// Проверка на null вместо ==
		log.debug(Objects.isNull(s1));
		log.debug(Objects.nonNull(s2));

		// Вместо s1.equals(s1) - которое свалится с NPE
		log.debug(Objects.equals(s1, s1));
		log.debug(Objects.equals(s1, s2));


		try {
			Objects.requireNonNull(s1);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}
	}
}
