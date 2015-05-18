package com.database.exception;

/**
 * SQL����
 * @author Shadow
 *
 */
public class DatabaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public DatabaseException(){
		super() ;
	}
	public DatabaseException(String message){
		super(message);
	}
	public DatabaseException(Throwable throwable){
		super(throwable);
	}
	public DatabaseException(String message , Throwable throwable){
		super(message,throwable);
	}
}
