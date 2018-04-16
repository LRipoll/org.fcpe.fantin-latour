package org.fcpe.fantinlatour.dao;

public class DataException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DataException(String message, Exception exception) {
		super(message, exception);

	}

}
