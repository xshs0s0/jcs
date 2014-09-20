package com.s0s0.app.search;

import java.io.File;
import java.util.List;

import com.s0s0.app.exception.JcsException;

public interface SearchHandlerInterface {
	
	public void handle(File file, Query query, SearchCallbackInterface callback, List<String> extensions) throws JcsException;
	
	public boolean isHandlble(String extension) throws JcsException;
}
