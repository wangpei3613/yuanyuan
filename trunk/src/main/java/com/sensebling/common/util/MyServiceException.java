package com.sensebling.common.util;

/**
 * Service异常 这个是抛到前台显示的，不用记录日志
 * @author  
 */
public class MyServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyServiceException() {
		super();		
	}

	public MyServiceException(String message, Throwable cause) {
		super(message, cause);		
	}

	public MyServiceException(String message) {
		super(message);		
	}

	public MyServiceException(Throwable cause) {
		super(cause);		
	}
}
