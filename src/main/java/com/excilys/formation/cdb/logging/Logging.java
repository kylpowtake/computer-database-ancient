package com.excilys.formation.cdb.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
	private static Logger logger = LoggerFactory.getLogger(Logging.class);

	public static Logger getLogger() {
		return logger;
	}
}
