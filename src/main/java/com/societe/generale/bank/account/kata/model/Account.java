package com.societe.generale.bank.account.kata.model;

import java.util.ArrayList;
import java.util.List;

import com.societe.generale.bank.account.kata.exception.InsufficientFundsException;
import com.societe.generale.bank.account.kata.exception.InvalidOperationException;


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
	
	public void deposit(Float amount) throws InsufficientFundsException, InvalidOperationException {
		new Operation(this, amount, OperationType.DEPOSIT).execute();
	}
	
	public void withdraw(Float amount) throws InsufficientFundsException, InvalidOperationException {
		new Operation(this, amount, OperationType.WITHDRAWAL).execute();
	}
}
