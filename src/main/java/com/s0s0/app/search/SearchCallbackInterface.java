package com.s0s0.app.search;

import com.s0s0.app.exception.JcsException;

public interface SearchCallbackInterface {

	public void callback(SearchResult result) throws JcsException ;
}