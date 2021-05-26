package com.societe.generale.bank.account.kata.model;

import java.math.BigDecimal;

public final class Amount {
	private final BigDecimal value;
	
	public Amount(BigDecimal value) throws InvalidAmountException {
		super();
		if(value == null) {
			throw new InvalidAmountException("Trying to create an invalid amount");
		}
		this.value = value;
	}
	
	public Amount add(Amount amount) throws InvalidAmountException {
		return new Amount(this.value.add(amount.value));
	}
	
	public Amount subtract(Amount amount) throws InvalidAmountException {
		return new Amount(this.value.subtract(amount.value));
	}
	
	public Boolean isPositive() {
		return this.value.compareTo(BigDecimal.ZERO) > 0;
	}
	
	public BigDecimal value() {
		return this.value;
	}
	
	public String toString() {
		return this.value.toString();
	}
}
