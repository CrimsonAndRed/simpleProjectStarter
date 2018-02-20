package log4j.model;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class NoDebugObject {

	public static void logMe() {
		log.debug("Constructed NoDebug");
		log.warn("Constructed NoDebug");
	}
}

