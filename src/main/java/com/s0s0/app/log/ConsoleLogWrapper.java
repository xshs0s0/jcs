package com.s0s0.app.log;

public class ConsoleLogWrapper implements LogWrapperInterface {
	
	public void error(String message) {
		System.out.println(message);
	}

	public void warn(String message) {
		System.out.println(message);
	}

	public void info(String message) {
		System.out.println(message);
	}

	public void debug(String message) {
		System.out.println(message);
	}

	public void error(Exception e) {
		error(e.getMessage());
	}

	public void warn(Exception e) {
		warn(e.getMessage());
	}

	public void info(Exception e) {
		info(e.getMessage());
	}

	public void debug(Exception e) {
		debug(e.getMessage());
	}

	public void error(Exception e, String message) {
		error(e.getMessage());
		error(message);
	}

	public void warn(Exception e, String message) {
		warn(e.getMessage());
		warn(message);
	}

	public void info(Exception e, String message) {
		info(e.getMessage());
		info(message);
		
	}

	public void debug(Exception e, String message) {
		debug(e.getMessage());
		debug(message);
	}

}
