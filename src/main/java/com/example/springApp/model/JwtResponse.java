package com.example.springApp.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1632498796416489223L;
	private final String jwttoken;
	private final String tokenType = "Bearer";

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
	
	public String getTokenType() {
		return this.tokenType;
	}
}
