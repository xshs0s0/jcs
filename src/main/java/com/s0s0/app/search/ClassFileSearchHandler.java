package com.s0s0.app.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.util.TextUtil;

public class ClassFileSearchHandler implements SearchHandlerInterface {

	public void handle(File file, Query query, SearchCallbackInterface callback, List<String> extensions) throws JcsException {
		SearchResult result = new SearchResult();
		List<ResultField> resultfields = new ArrayList<ResultField>();
		QueryTypeEnum type = query.getType();
		TextMatcherInterface textmatcher = null;
		boolean casesensitive = true;
		if (type==QueryTypeEnum.CASE_INSENSITIVE_TEXT_MATCH)
		{
			textmatcher = new StandardTextMatcher();
			casesensitive = false;
		} else if (type==QueryTypeEnum.CASE_SENSITIVE_TEXT_MATCH)
		{
			textmatcher = new StandardTextMatcher();
		} else if (type==QueryTypeEnum.CASE_INSENSITIVE_REGEX_MATCH)
		{
			casesensitive = false;
			textmatcher = new RegexTextMatcher();
		} else if (type==QueryTypeEnum.CASE_SENSITIVE_REGEX_MATCH)
		{
			textmatcher = new RegexTextMatcher();
		}
		if (textmatcher!=null)
		{
			if (textmatcher.match(query.getValue(), file.getName(), casesensitive)) {
				List<String> rf = query.getRf();
				if ((rf==null)|| rf.contains("path"))
				{
					resultfields.add(new StringResultField("path",file.getParent()));
				}
				if ((rf==null)|| rf.contains("filename"))
				{
					resultfields.add(new StringResultField("filename",file.getName()));
				}
				if ((rf==null)|| rf.contains("classname"))
				{
					resultfields.add(new StringResultField("classname",file.getName()));
				}
				result.setFields(resultfields);
				callback.callback(result);
			}
		}
	}

	public boolean isHandlble(String extension) throws JcsException {
		if (extension.equalsIgnoreCase("class"))
			return true;
		return false;
	}

}
