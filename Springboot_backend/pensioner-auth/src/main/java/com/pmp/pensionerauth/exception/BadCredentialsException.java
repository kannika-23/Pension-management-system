package com.pmp.pensionerauth.exception;

public class BadCredentialsException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadCredentialsException(String msg) {
		super(msg);
	}

}
