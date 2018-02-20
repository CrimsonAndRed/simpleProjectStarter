package log4j.some;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Outer {

	public static void logMe() {
		log.debug("Outer");
		log.error("Outer");
	}
}
