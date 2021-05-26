package com.societe.generale.bank.account.kata.model;

import java.util.Date;

public class Operation {
	private Account targetAccount;
	private Amount amount;
	private Date date;
	private OperationType type;
	private Amount newBalance;
	
	public Operation(Account targetAccount, Amount amount, OperationType type) throws InvalidOperationException {
		super();
		if(targetAccount == null || amount == null || type == null || !Boolean.TRUE.equals(amount.isPositive())) {
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
	
	public Amount amount() {
		return amount;
	}
	
	public Date date() {
		return date;
	}
	
	public Amount newBalance() {
		return newBalance;
	}
	
	public void execute() throws InsufficientFundsException, InvalidAmountException {
		Amount nextBalance = type.equals(OperationType.DEPOSIT) 
				? this.targetAccount().balance().add(this.amount)
				: this.targetAccount().balance().subtract(this.amount);
		if(!Boolean.TRUE.equals(nextBalance.isPositive())) {
			throw new InsufficientFundsException(
					String.format(
							"Trying to withdraw %s but current account balance is only %s",
							amount.toString(),
							this.targetAccount().balance()
						)
					);
		}
		if(type.equals(OperationType.DEPOSIT)) {
			this.targetAccount().increaseBalance(this.amount);
		}
		else {
			this.targetAccount().decreaseBalance(this.amount);
		}
		this.newBalance = nextBalance;
		this.date = new Date();
		this.targetAccount().operations().add(this);
	}
	
	public String toString() {
		if(date == null) {
			return String.format(
					"NOT YET EXECUTED | %s | %s |",
					type.toString(),
					amount.toString()
				);
		}
		return String.format(
				"%s | %s | %s | %s",
				date.toString(),
				type.toString(),
				amount.toString(),
				newBalance.toString()
			);
	}
}
