package com.s0s0.app.exception;

public class JcsException extends Exception {

	public JcsException(String message)
	{
		super(message);
	}
	
	public JcsException(Exception e)
	{
		super(e);
	}
	
	public JcsException(String message, Exception e)
	{
		super(message, e);
	}
}
