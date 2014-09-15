package com.s0s0.app.text.language;

import java.util.HashMap;
import java.util.Map;

import com.s0s0.app.text.TextCode;
import com.s0s0.app.text.TextRepoInterface;

public class En implements TextRepoInterface {
	
	private static final Map<String, String> textmapping;
	static {
		textmapping = new HashMap<String, String>();
		textmapping.put(TextCode.TXT_FEL_01, "");
		textmapping.put(TextCode.TXT_FEL_02, "");
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
