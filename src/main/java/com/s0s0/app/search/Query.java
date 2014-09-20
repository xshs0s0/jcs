package com.s0s0.app.search;

import java.util.List;

public class Query {

	private QueryTypeEnum type;
	private String value;
	private List<String> rf; // return fields
	
	public Query()
	{
	}

	public Query(QueryTypeEnum type, String value, List<String> rf) {
		super();
		this.type = type;
		this.value = value;
		this.rf = rf;
	}

	public QueryTypeEnum getType() {
		return type;
	}

	public void setType(QueryTypeEnum type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getRf() {
		return rf;
	}

	public void setRf(List<String> rf) {
		this.rf = rf;
	}
}