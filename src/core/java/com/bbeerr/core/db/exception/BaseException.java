package com.bbeerr.core.db.exception;

@SuppressWarnings("serial")
public class BaseException extends Exception {

	protected String message;

	@Override
	public String getLocalizedMessage() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}