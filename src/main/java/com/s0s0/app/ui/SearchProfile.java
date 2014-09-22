package com.s0s0.app.ui;

import java.util.List;

public class SearchProfile {

	private List<String> paths;
	private String query;
	private boolean regex;
	private boolean casesensitive;
	
	public SearchProfile()
	{
	}
	
	public SearchProfile(List<String> paths, String query, boolean regex,
			boolean casesensitive) {
		super();
		this.paths = paths;
		this.query = query;
		this.regex = regex;
		this.casesensitive = casesensitive;
	}
	
	public List<String> getPaths() {
		return paths;
	}
	
	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public boolean isRegex() {
		return regex;
	}
	
	public void setRegex(boolean regex) {
		this.regex = regex;
	}
	
	public boolean isCasesensitive() {
		return casesensitive;
	}

	public void setCasesensitive(boolean casesensitive) {
		this.casesensitive = casesensitive;
	}
	
	public void addPath(String path)
	{
		if (!paths.contains(path))
			paths.add(path);
	}
	
	public boolean deletPath(String path)
	{
		return paths.remove(path);
	}
}