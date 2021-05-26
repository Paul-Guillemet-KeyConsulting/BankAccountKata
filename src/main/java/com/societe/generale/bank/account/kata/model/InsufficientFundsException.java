package com.societe.generale.bank.account.kata.model;

public class InsufficientFundsException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5309242127873671582L;

	public InsufficientFundsException(String message) {
		super(message);
	}
}
