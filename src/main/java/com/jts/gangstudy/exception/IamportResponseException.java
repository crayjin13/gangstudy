package com.jts.gangstudy.exception;

import org.apache.http.HttpException;

public class IamportResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1108159593517976752L;
	private int httpStatusCode;
	
	public IamportResponseException(String error, HttpException exception) {
		super( error, exception );
		
		this.httpStatusCode = exception.hashCode();
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

}
