package log4j.some.pack;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Inner {

	public static void logMe() {
		log.debug("Inner");
		log.error("Inner");
	}
}
