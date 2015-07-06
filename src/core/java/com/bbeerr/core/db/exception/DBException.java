package com.bbeerr.core.db.exception;

public class DBException extends BaseException {

	private static final long serialVersionUID = -5822759870702599001L;

	public DBException(String msg) {
		this.message = msg;
	}

}