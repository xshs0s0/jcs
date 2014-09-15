package com.s0s0.app.log;

public interface LogWrapperInterface {

	public void error(String message);
	
	public void warn(String message);
	
	public void info(String message);
	
	public void debug(String message);
	
    public void error(Exception e);
	
	public void warn(Exception e);
	
	public void info(Exception e);
	
	public void debug(Exception e);
	
    public void error(Exception e, String message);
	
	public void warn(Exception e, String message);
	
	public void info(Exception e, String message);
	
	public void debug(Exception e, String message);
}
