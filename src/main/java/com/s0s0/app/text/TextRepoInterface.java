package com.s0s0.app.text;

import java.util.Map;

public interface TextRepoInterface {

	public String getText(String textcode);
	
	public Map<String,String> getTextMapping();
}
