package com.societe.generale.bank.account.kata.model;

import java.util.ArrayList;
import java.util.List;


public class Account {
	private Client owner;
	private Float balance;
	private List<Operation> operations; 
	
	public Account(Client owner) {
		super();
		this.owner = owner;
		this.balance = 0f;
		this.operations = new ArrayList<>();
	}

	public Client owner() {
		return owner;
	}
	
	public Float balance() {
		return balance;
	}
	
	public List<Operation> operations() {
		return operations;
	}
	
	public void modifyBalance(Float newBalance) {
		balance = newBalance;
	}
}
