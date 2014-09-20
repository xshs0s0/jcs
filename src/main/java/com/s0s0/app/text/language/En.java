package com.s0s0.app.text.language;

import java.util.HashMap;
import java.util.Map;

import com.s0s0.app.text.TextCode;
import com.s0s0.app.text.TextRepoInterface;

public class En implements TextRepoInterface {
	
	private static final Map<String, String> textmapping;
	static {
		textmapping = new HashMap<String, String>();
		textmapping.put(TextCode.TXT_FEL_01, "File extension is null or empty");
		textmapping.put(TextCode.TXT_FEL_02, "File extension already exists");
		textmapping.put(TextCode.TXT_TM_01, "Null search string or content value");
	}
	
	public En()
	{
	}
	
	public String getText(String textcode)
	{
		return textmapping.get(textcode);
	}

	public Map<String, String> getTextMapping() {
		return textmapping;
	}
	
}