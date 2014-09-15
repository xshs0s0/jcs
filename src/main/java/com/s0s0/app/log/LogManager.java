package com.s0s0.app.log;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.text.ApplicationError;
import com.s0s0.app.text.TextManagerAbstract;

public class LogManager {
	
	private LogWrapperInterface logger = null;
	private TextManagerAbstract textmanager = null;
	
	public void errorTextCode(String textcode)
	{
		String message = ApplicationError.INVALID_TEXTCODE_ERROR;
		 
		try {
			message = textmanager.getText(textcode); 
		} catch (JcsException e)
		{
			message += " " + textcode;
		}
		error(message);
	}
	
	public void warnTextCode(String textcode)
	{
		String message = ApplicationError.INVALID_TEXTCODE_ERROR;
		 
		try {
			message = textmanager.getText(textcode); 
		} catch (JcsException e)
		{
			message += " " + textcode;
		}
		warn(message);
	}
	
	public void infoTextCode(String textcode)
	{
		String message = ApplicationError.INVALID_TEXTCODE_ERROR;
		 
		try {
			message = textmanager.getText(textcode); 
		} catch (JcsException e)
		{
			message += " " + textcode;
		}
		info(message);
	}
	
	public void debugTextCode(String textcode)
	{
		String message = ApplicationError.INVALID_TEXTCODE_ERROR;
		 
		try {
			message = textmanager.getText(textcode); 
		} catch (JcsException e)
		{
			message += " " + textcode;
		}
		debug(message);
	}
	
	public void error(String message) {
		logger.error(message);
	}

	public void warn(String message) {
		logger.warn(message);
	}

	public void info(String message) {
		logger.info(message);
	}

	public void debug(String message) {
		logger.debug(message);
	}

	public void error(Exception e) {
		logger.error(e);
	}

	public void warn(Exception e) {
		logger.warn(e);
	}

	public void info(Exception e) {
		logger.info(e);
	}

	public void debug(Exception e) {
		logger.debug(e);
	}

	public void error(Exception e, String message) {
		logger.error(e, message);
	}

	public void warn(Exception e, String message) {
		logger.warn(e, message);
	}

	public void info(Exception e, String message) {
		logger.info(e, message);
		
	}

	public void debug(Exception e, String message) {
		logger.debug(e, message);
	}
	
	public LogWrapperInterface getLogger() {
		return logger;
	}

	public void setLogger(LogWrapperInterface logger) {
		this.logger = logger;
	}

	public TextManagerAbstract getTextmanager() {
		return textmanager;
	}

	public void setTextmanager(TextManagerAbstract textmanager) {
		this.textmanager = textmanager;
	}
}
