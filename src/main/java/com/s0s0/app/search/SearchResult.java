package com.s0s0.app.search;

import java.util.List;

public class SearchResult {

	private List<ResultField> fields;
	
	public SearchResult()
	{
	}

	public List<ResultField> getFields() {
		return fields;
	}

	public void setFields(List<ResultField> fields) {
		this.fields = fields;
	}
}
