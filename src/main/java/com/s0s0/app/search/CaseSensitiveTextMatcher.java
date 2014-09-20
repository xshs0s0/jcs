package com.s0s0.app.search;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.text.TextCode;

public class CaseSensitiveTextMatcher implements TextMatcherInterface {

	public CaseSensitiveTextMatcher()
	{
	}
	
	public boolean match(String query, String value) throws JcsException {
		if ((query == null) || (value == null))
		{
			throw new JcsException(TextCode.TXT_TM_01);
		}
		if (value.contains(query))
			return true;
		else
			return false;
	}

}