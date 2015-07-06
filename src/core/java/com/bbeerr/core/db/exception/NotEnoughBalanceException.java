package com.bbeerr.core.db.exception;

@SuppressWarnings("serial")
public class NotEnoughBalanceException extends BaseException {

	public NotEnoughBalanceException(String msg) {
		this.message = msg;
	}

}