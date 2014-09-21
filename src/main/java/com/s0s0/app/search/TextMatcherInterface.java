package com.s0s0.app.search;

import com.s0s0.app.exception.JcsException;

public interface TextMatcherInterface {

	public boolean match(String query, String value, boolean casesensitive) throws JcsException;
}
