package com.societe.generale.bank.account.kata.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Account implements Historisable{
	private Client owner;
	private Amount balance;
	private List<Operation> operations; 
	
	public Account(Client owner) throws InvalidAmountException {
		super();
		this.owner = owner;
		this.balance = new Amount(new BigDecimal(0));
		this.operations = new ArrayList<>();
	}

	public Client owner() {
		return owner;
	}
	
	public Amount balance() {
		return balance;
	}
	
	public List<Operation> operations() {
		return operations;
	}
	
	public void increaseBalance(Amount amount) throws InvalidAmountException {
		balance = balance.add(amount);
	}
	
	public void decreaseBalance(Amount amount) throws InvalidAmountException {
		balance = balance.subtract(amount);
	}
	
	public void deposit(Amount amount) throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		new Operation(this, amount, OperationType.DEPOSIT).execute();
	}
	
	public void withdraw(Amount amount) throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		new Operation(this, amount, OperationType.WITHDRAWAL).execute();
	}
	
	public String getOperationHistoryAsString() {
		return operations().stream()
			.map(Operation::toString)
			.collect(Collectors.joining("\n"));
	}

	public void printHistory() {
		System.out.println(getOperationHistoryAsString());
	}
}
