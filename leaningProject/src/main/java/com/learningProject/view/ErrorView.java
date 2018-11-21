package com.learningProject.view;

public class ErrorView {

	private final String message;
	private final String key;
	
	public ErrorView(String message, String key) {
		super();
		this.message = message;
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public String getKey() {
		return key;
	}
}
