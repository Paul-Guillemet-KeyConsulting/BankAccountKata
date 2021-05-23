package com.societe.generale.bank.account.kata.model;

import java.util.Date;

import com.societe.generale.bank.account.kata.exception.InvalidOperationException;

public class Operation {
	private Account targetAccount;
	private Float amount;
	private Date date;
	private OperationType type;
	private Float newBalance;
	
	public Operation(Account targetAccount, Float amount, OperationType type) throws InvalidOperationException {
		super();
		if(targetAccount == null || amount == null || type == null || amount <= 0) {
			throw new InvalidOperationException("Trying to create an invalid operation");
		}
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.type = type;
	}

	public Account targetAccount() {
		return targetAccount;
	}
	
	public OperationType type() {
		return type;
	}
	
	public Float amount() {
		return amount;
	}
	
	public Date date() {
		return date;
	}
	
	public Float newBalance() {
		return newBalance;
	}
}
