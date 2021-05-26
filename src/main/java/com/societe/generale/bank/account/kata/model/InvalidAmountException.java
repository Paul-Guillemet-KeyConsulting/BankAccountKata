package com.societe.generale.bank.account.kata.model;

public class InvalidAmountException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1553098248010891240L;

	public InvalidAmountException(String message) {
		super(message);
	}
}
