package com.s0s0.app.search;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.text.TextCode;

public class CaseInsensitiveTextMatcher implements TextMatcherInterface {

	public CaseInsensitiveTextMatcher()
	{
	}
	
	public boolean match(String query, String value) throws JcsException {
		if ((query == null) || (value == null))
		{
			throw new JcsException(TextCode.TXT_TM_01);
		}
		String lowercase = query.toLowerCase();
		if (value.toLowerCase().contains(lowercase))
			return true;
		else
			return false;
	}
}