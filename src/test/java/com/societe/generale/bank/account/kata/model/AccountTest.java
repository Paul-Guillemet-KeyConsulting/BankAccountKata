package com.societe.generale.bank.account.kata.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

	private Client client = new Client();

	@Test
	public void shouldHaveCorrectClientWhenInstanciated() throws InvalidAmountException {
		Account account  = new Account(client);
		
		Assert.assertEquals(client, account.owner());
	}
	
	// Deposit
	@Test
	public void shouldFailWhenTryingToDepositNullAmount() throws InvalidAmountException {
		Account account  = new Account(client);
		
		assertThatThrownBy(() -> {
			new Operation(account, null, OperationType.DEPOSIT);
		})
			.isInstanceOf(InvalidOperationException.class)
			.hasMessage("Trying to create an invalid operation");
		Assert.assertEquals(BigDecimal.ZERO, account.balance().value());
		Assert.assertTrue(account.operations().isEmpty());
	}
	
	@Test
	public void shouldFailWhenTryingToDepositInvalidAmount() throws InvalidAmountException {
		Account account  = new Account(client);
		
		assertThatThrownBy(() -> {
			new Operation(account, new Amount(new BigDecimal(-1)), OperationType.DEPOSIT);
		})
			.isInstanceOf(InvalidOperationException.class)
			.hasMessage("Trying to create an invalid operation");

		Assert.assertEquals(BigDecimal.ZERO, account.balance().value());
		Assert.assertTrue(account.operations().isEmpty());
		
	}
	
	@Test
	public void shouldUpdateBalanceAndCreateOperationWhenDepositValidAmount() throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		Account account  = new Account(client);
		
		account.deposit(new Amount(new BigDecimal(500)));
		
		Assert.assertEquals(new BigDecimal(500), account.balance().value());
		Assert.assertEquals(1, account.operations().size());		
	}
	
	// Withdrawal
	@Test
	public void shouldFailWhenTryingToWithdrawNullAmount() throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		Account account  = new Account(client);
		account.deposit(new Amount(new BigDecimal(500)));
		
		assertThatThrownBy(() -> {
			account.withdraw(null);
		})
			.isInstanceOf(InvalidOperationException.class)
			.hasMessage("Trying to create an invalid operation");
		Assert.assertEquals(new BigDecimal(500f), account.balance().value());
		Assert.assertEquals(1, account.operations().size());
		
	}
	
	@Test
	public void shouldFailWhenTryingToWithdrawInvalidAmount() throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		Account account  = new Account(client);
		account.deposit(new Amount(new BigDecimal(500)));
		
		assertThatThrownBy(() -> {
			account.withdraw(new Amount(new BigDecimal(-1)));
		})
			.isInstanceOf(InvalidOperationException.class)
			.hasMessage("Trying to create an invalid operation");
		Assert.assertEquals(new BigDecimal(500f), account.balance().value());
		Assert.assertEquals(1, account.operations().size());
		
	}
	
	@Test
	public void ShouldFailToWithdrawWhenInsufficientFunds() throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		Account account  = new Account(client);
		account.deposit(new Amount(new BigDecimal(500)));
		
		assertThatThrownBy(() -> {
			account.withdraw(new Amount(new BigDecimal(550)));
		})
			.isInstanceOf(InsufficientFundsException.class)
			.hasMessage("Trying to withdraw 550 but current account balance is only 500");
		Assert.assertEquals(new BigDecimal(500), account.balance().value());
		Assert.assertEquals(1, account.operations().size());
	}
	
	@Test
	public void shouldUpdateBalanceAndCreateOperaionWithdrawingValidAmount() throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		Account account  = new Account(client);
		account.deposit(new Amount(new BigDecimal(500)));
		
		account.withdraw(new Amount(new BigDecimal(200)));
		
		Assert.assertEquals(new BigDecimal(300), account.balance().value());
		Assert.assertEquals(2, account.operations().size());
	}
	
	@Test
	public void shouldHaveCorrectValueWhenConvertingHistoryToString() throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		Account account  = new Account(client);
		account.deposit(new Amount(new BigDecimal(500)));
		account.withdraw(new Amount(new BigDecimal(200)));
		
		String dateString = new Date().toString();
		String expectedString = new StringBuilder(dateString)
				.append(" | DEPOSIT | 500 | 500\n")
				.append(dateString)
				.append(" | WITHDRAWAL | 200 | 300")
				.toString();
		
		Assert.assertEquals(expectedString, account.getOperationHistoryAsString());
	}
	
	@Test
	public void shouldUpddateBalanceAndCreateOperaionWithdrawingValidAmount() throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		Account account  = new Account(client);
		account.deposit(new Amount(new BigDecimal(500)));
		account.withdraw(new Amount(new BigDecimal(200)));
		
		String dateString = new Date().toString();
		String expectedString = new StringBuilder(dateString)
				.append(" | DEPOSIT | 500 | 500\n")
				.append(dateString)
				.append(" | WITHDRAWAL | 200 | 300")
				.toString();
		
		Assert.assertEquals(expectedString, account.getOperationHistoryAsString());
	}
	
	@Test
	public void shouldPrintHisotryWhenCalled() throws InsufficientFundsException, InvalidOperationException, InvalidAmountException {
		Account account  = new Account(client);
		account.deposit(new Amount(new BigDecimal(500)));
		account.withdraw(new Amount(new BigDecimal(200)));
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(stream));
		String dateString = new Date().toString();
		String expectedString = new StringBuilder(dateString)
				.append(" | DEPOSIT | 500 | 500\n")
				.append(dateString)
				.append(" | WITHDRAWAL | 200 | 300\n")
				.toString();
		
		try {
			account.printHistory();
		}
		catch(Exception e) {
			Assert.fail(e.getMessage());
		}
		finally {
			System.setOut(originalOut);
		}
		
		Assert.assertEquals(expectedString, stream.toString());
	}
	
	

}
