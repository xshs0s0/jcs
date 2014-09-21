package com.s0s0.app.search;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.text.TextCode;

public class StandardTextMatcher implements TextMatcherInterface {

	public StandardTextMatcher()
	{
	}
	
	public boolean match(String query, String value, boolean casesensitive) throws JcsException {
		if ((query == null) || (value == null))
		{
			throw new JcsException(TextCode.TXT_TM_01);
		}
		if (!casesensitive)
		{
			query = query.toLowerCase();
			value = value.toLowerCase();
		}
		if (value.contains(query))
				return true;
		return false;
	}
}