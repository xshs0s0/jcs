package com.s0s0.app.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.text.TextCode;

public class RegexTextMatcher implements TextMatcherInterface {
	public RegexTextMatcher()
	{
	}
	
	public boolean match(String query, String value) throws JcsException {
		if ((query == null) || (value == null))
		{
			throw new JcsException(TextCode.TXT_TM_01);
		}
		Matcher matcher;
		try {
			Pattern pattern = Pattern.compile(query);
			matcher = pattern.matcher(value);
			if (matcher.matches())
				return true;
		} catch (Exception e) {
			throw new JcsException(e);
		}
		return false;
	}
}