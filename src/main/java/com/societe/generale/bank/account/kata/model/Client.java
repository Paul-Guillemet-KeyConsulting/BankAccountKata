package com.societe.generale.bank.account.kata.model;

public class Client {
	private String firstName;
	private String lastName;
	
	public Client(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String firstName() {
		return firstName;
	}
	
	public String lastName() {
		return lastName;
	}
	
}
