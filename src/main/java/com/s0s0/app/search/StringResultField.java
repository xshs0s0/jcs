package com.s0s0.app.search;

public class StringResultField extends ResultField {

	private String value = null;
	
	public StringResultField() {
		super();
	}
	
	public StringResultField(String name, String value) {
		super(name);
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
