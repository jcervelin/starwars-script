package com.starwars.util;

public class JSONBasicInfo {
	
	public JSONBasicInfo() {
		super();
	}

	public JSONBasicInfo(String message) {
		super();
		this.message = message;
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}