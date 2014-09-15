package com.s0s0.app.text;

import java.util.List;
import java.util.Map;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.util.TextUtil;

public abstract class TextManagerAbstract {

	private String language = "en";
	
	public abstract String getText(String textcode) throws JcsException;
	
	public abstract Map<String, String> getTextMapping() throws JcsException;
	
	public abstract List<String> getSupportedLanguageList() throws JcsException;
	
	public abstract TextRepoInterface getTextRepo() throws JcsException;
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		if ((language != null) && !language.isEmpty())
		{
			this.language = language.toLowerCase();
		}
	}

	public List<String> getTextCodeList() {
		return TextUtil.getStaticStringFieldNames(TextCode.class);
	}
	
}