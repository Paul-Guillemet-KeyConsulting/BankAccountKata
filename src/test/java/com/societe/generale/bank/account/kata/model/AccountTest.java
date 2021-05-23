package com.societe.generale.bank.account.kata.model;

import org.junit.Assert;
import org.junit.Test;

import com.societe.generale.bank.account.kata.exception.InsufficientFundsException;
import com.societe.generale.bank.account.kata.exception.InvalidOperationException;

public class AccountTest {

	private Client client = new Client("Paul", "Guillemet");

	@Test
	public void accountInstanciation() {
		Account account  = new Account(client);
		Assert.assertEquals(client, account.owner());
	}
	
	// Deposit
	@Test
	public void accountDepositNullAmount() {
		Account account  = new Account(client);
		try {
			account.deposit(null);
			Assert.fail("Should have triggered InvalidOperationException");
		}
		catch(Exception e) {
			Assert.assertTrue(e instanceof InvalidOperationException);
			Assert.assertEquals("Trying to create an invalid operation", e.getMessage());
			Assert.assertEquals(Float.valueOf(0), account.balance());
			Assert.assertTrue(account.operations().isEmpty());
		}
	}
	
	@Test
	public void accountDepositInvalidAmount() {
		Account account  = new Account(client);
		try {
			account.deposit(-1f);
			Assert.fail("Should have triggered InvalidOperationException");
		}
		catch(Exception e) {
			Assert.assertTrue(e instanceof InvalidOperationException);
			Assert.assertEquals("Trying to create an invalid operation", e.getMessage());
			Assert.assertEquals(Float.valueOf(0), account.balance());
			Assert.assertTrue(account.operations().isEmpty());
		}
	}
	
	@Test
	public void accountDepositValidAmount() throws InsufficientFundsException, InvalidOperationException {
		Account account  = new Account(client);
		account.deposit(500f);
		Assert.assertEquals(Float.valueOf(500f), account.balance());
		Assert.assertEquals(1, account.operations().size());		
	}
	
	// Withdrawal
	@Test
	public void accountWithdrawNullAmount() throws InsufficientFundsException, InvalidOperationException {
		Account account  = new Account(client);
		account.deposit(500f);
		try {
			account.withdraw(null);
			Assert.fail("Should have triggered InvalidOperationException");
		}
		catch(Exception e) {
			Assert.assertTrue(e instanceof InvalidOperationException);
			Assert.assertEquals("Trying to create an invalid operation", e.getMessage());
			Assert.assertEquals(Float.valueOf(500f), account.balance());
			Assert.assertEquals(1, account.operations().size());
		}
	}
	
	@Test
	public void accountWithdrawInvalidAmount() throws InsufficientFundsException, InvalidOperationException {
		Account account  = new Account(client);
		account.deposit(500f);
		try {
			account.withdraw(-1f);
			Assert.fail("Should have triggered InvalidOperationException");
		}
		catch(Exception e) {
			Assert.assertTrue(e instanceof InvalidOperationException);
			Assert.assertEquals("Trying to create an invalid operation", e.getMessage());
			Assert.assertEquals(Float.valueOf(500f), account.balance());
			Assert.assertEquals(1, account.operations().size());
		}
	}
	
	@Test
	public void accountWithdrawWhenInsufficientFunds() throws InsufficientFundsException, InvalidOperationException {
		Account account  = new Account(client);
		account.deposit(500f);
		try {
			account.withdraw(550f);
			Assert.fail("Should have triggered InsufficientFundsException");
		}
		catch(Exception e) {
			Assert.assertTrue(e instanceof InsufficientFundsException);
			Assert.assertEquals(
					"Trying to withdraw 550,00 but current account balance is only 500,00",
					e.getMessage()
					);
			Assert.assertEquals(Float.valueOf(500f), account.balance());
			Assert.assertEquals(1, account.operations().size());
		}
	}
	
	@Test
	public void accountWithdrawValidAmount() throws InsufficientFundsException, InvalidOperationException {
		Account account  = new Account(client);
		account.deposit(500f);
		
		account.withdraw(200f);
		Assert.assertEquals(Float.valueOf(300f), account.balance());
		Assert.assertEquals(2, account.operations().size());
	}

	

}
