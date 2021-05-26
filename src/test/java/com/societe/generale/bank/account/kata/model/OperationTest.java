package com.societe.generale.bank.account.kata.model;

import static org.mockito.Mockito.never;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

public class OperationTest {

	private final String INVAL_OP_MSG = "Trying to create an invalid operation";
	private Client client = new Client();
	

	// Operation creation
	@Test
	public void shouldFailWhenTryingToInstanciateWithNullType() throws InvalidAmountException {
		Account account = new Account(client);
		
		assertThatThrownBy(() -> {
			new Operation(account, new Amount(new BigDecimal(500)), null);
		})
			.isInstanceOf(InvalidOperationException.class)
			.hasMessage(INVAL_OP_MSG);
	}
	
	@Test
	public void shouldFailWhenTryingToInstanciateWithNullAccount() throws InvalidAmountException {
		
		assertThatThrownBy(() -> {
			new Operation(null, new Amount(new BigDecimal(500)), OperationType.DEPOSIT);
		})
			.isInstanceOf(InvalidOperationException.class)
			.hasMessage(INVAL_OP_MSG);
	}
	
	@Test
	public void shouldFailWhenTryingToInstanciateWithNullAmount() throws InvalidAmountException {
		Account account = new Account(client);
		
		assertThatThrownBy(() -> {
			new Operation(account, null, OperationType.DEPOSIT);
		})
			.isInstanceOf(InvalidOperationException.class)
			.hasMessage(INVAL_OP_MSG);
	}
	
	@Test
	public void shouldFailWhenTryingToInstanciateWithNegativeAmount() throws InvalidAmountException {
		Account account = new Account(client);
		
		assertThatThrownBy(() -> {
			new Operation(account, new Amount(new BigDecimal(-1)), OperationType.DEPOSIT);
		})
			.isInstanceOf(InvalidOperationException.class)
			.hasMessage(INVAL_OP_MSG);
	}
	
	@Test
	public void shouldHaveCorrectAttributesWhenInstanciatedWhitValidArgs() throws InvalidOperationException, InvalidAmountException {
		Account account = new Account(client);
		
		Operation op = new Operation(account, new Amount(new BigDecimal(20)), OperationType.DEPOSIT);
		
		Assert.assertEquals(account, op.targetAccount());
		Assert.assertEquals(new BigDecimal(20), op.amount().value());
		Assert.assertEquals(OperationType.DEPOSIT, op.type());
	}
	
		// Operation execution
		@Test
		public void shouldUpdateAccountAndCompleteOwnAttributesWhenDepositIsExecuted() throws InvalidOperationException, InsufficientFundsException, InvalidAmountException {
			Account account = Mockito.mock(Account.class);
			ArrayList<Operation> history = new ArrayList<>();
			Mockito.when(account.balance())
				.thenReturn(new Amount(new BigDecimal(500)));
			Mockito.when(account.operations())
				.thenReturn(history);

			Operation op = new Operation(account, new Amount(new BigDecimal(20)), OperationType.DEPOSIT);
			op.execute();
			
			Assert.assertEquals(new BigDecimal(520), op.newBalance().value());
			Assert.assertEquals(history.get(0), op);
			Assert.assertNotNull(op.date());
			Mockito.verify(account).increaseBalance(Mockito.any(Amount.class));
			
		}
		
		@Test
		public void shouldUpdateAccountAndCompleteOwnAttributesWhenWithdrawalIsExecuted() throws InvalidOperationException, InsufficientFundsException, InvalidAmountException {
			Account account = Mockito.mock(Account.class);
			ArrayList<Operation> history = new ArrayList<>();
			Mockito.when(account.balance())
				.thenReturn(new Amount(new BigDecimal(500)));
			Mockito.when(account.operations())
				.thenReturn(history);

			Operation op = new Operation(account, new Amount(new BigDecimal(20)), OperationType.WITHDRAWAL);
			op.execute();
			
			Assert.assertEquals(new BigDecimal(480), op.newBalance().value());
			Assert.assertEquals(history.get(0), op);
			Assert.assertNotNull(op.date());
			Mockito.verify(account).decreaseBalance(Mockito.any(Amount.class));
		}
		
		@Test
		public void shouldFailWhenWithdrawalExecutionWithInsufficientFunds() throws InvalidOperationException, InsufficientFundsException, InvalidAmountException {
			Account account = Mockito.mock(Account.class);
			ArrayList<Operation> history = new ArrayList<>();
			Mockito.when(account.balance())
				.thenReturn(new Amount(new BigDecimal(500)));
			Mockito.when(account.operations())
				.thenReturn(history);
			Operation op = new Operation(account, new Amount(new BigDecimal(550)), OperationType.WITHDRAWAL);
			
			assertThatThrownBy(() -> {
				op.execute();
			})
				.isInstanceOf(InsufficientFundsException.class)
				.hasMessage("Trying to withdraw 550 but current account balance is only 500");
			Mockito.verify(account, never()).decreaseBalance(Mockito.any());
			Assert.assertTrue(history.isEmpty());
		}
		
		@Test
		public void shouldMatchExpectationsWhenUnexecutedOpConvertedToString() throws InvalidOperationException, InsufficientFundsException, InvalidAmountException {
			Account account = Mockito.mock(Account.class);
			
			Operation op = new Operation(account, new Amount(new BigDecimal(550)), OperationType.WITHDRAWAL);
			
			Assert.assertEquals("NOT YET EXECUTED | WITHDRAWAL | 550 |", op.toString());
		}
		
		@Test
		public void shouldMatchExpectationsWhenExecutedOpConvertedToString() throws InvalidOperationException, InsufficientFundsException, InvalidAmountException {
			Account account = Mockito.mock(Account.class);
			ArrayList<Operation> history = new ArrayList<>();
			Mockito.when(account.balance())
				.thenReturn(new Amount(new BigDecimal(500)));
			Mockito.when(account.operations())
				.thenReturn(history);
			
			Operation op = new Operation(account, new Amount(new BigDecimal(550)), OperationType.DEPOSIT);
			op.execute();
			
			Assert.assertEquals(
					String.format("%s | DEPOSIT | 550 | 1050", new Date().toString()),
					op.toString());
		}
			

}
