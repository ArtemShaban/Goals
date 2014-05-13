package com.shaban.goals.exception;

import com.shaban.goals.log.Logger;

@SuppressWarnings("serial")
public class InternalException extends Exception {

	private final Logger logger = new Logger(this);
	private final static String DESCRIPTION = "The Exception rised programmaticaly";
	
	public InternalException() {
		super(DESCRIPTION);
		logger.e(DESCRIPTION, this);
	}
	
	public InternalException(String detailMessage) {
		super(detailMessage);
		logger.e(detailMessage, this);
	}
	
}
