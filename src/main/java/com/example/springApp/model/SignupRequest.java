package com.example.springApp.model;

import java.io.Serializable;

public class SignupRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3213742999535079887L;

	private String username;
	private String password;
	private String salary;
	private String address;
	private String phone;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
