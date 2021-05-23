package com.societe.generale.bank.account.kata.model;

import static org.mockito.Mockito.never;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.societe.generale.bank.account.kata.exception.InsufficientFundsException;
import com.societe.generale.bank.account.kata.exception.InvalidOperationException;

public class OperationTest {

	private final String INVAL_OP_MSG = "Trying to create an invalid operation";
	private Client client = new Client("Paul", "Guillemet");
	private Account account = new Account(client);

	// Operation creation
	@Test
	public void operationInstanciationNullType() {
		try {
			new Operation(account, 500f, null);
			Assert.fail("Should have triggered InvalidOperationException");
		}
		catch(Exception e) {
			Assert.assertTrue(e instanceof InvalidOperationException);
			Assert.assertEquals(INVAL_OP_MSG,e.getMessage());
		}
	}
	
	@Test
	public void operationInstanciationNullAccount() {
		try {
			new Operation(null, 500f, OperationType.DEPOSIT);
			Assert.fail("Should have triggered InvalidOperationException");
		}
		catch(Exception e) {
			Assert.assertTrue(e instanceof InvalidOperationException);
			Assert.assertEquals(INVAL_OP_MSG,e.getMessage());
		}
	}
	
	@Test
	public void operationInstanciationNullAmount() {
		try {
			new Operation(account, null, OperationType.DEPOSIT);
			Assert.fail("Should have triggered InvalidOperationException");
		}
		catch(Exception e) {
			Assert.assertTrue(e instanceof InvalidOperationException);
			Assert.assertEquals(INVAL_OP_MSG,e.getMessage());
		}
	}
	
	@Test
	public void operationInstanciationNegativeAmount() {
		try {
			new Operation(account, -1f, OperationType.DEPOSIT);
			Assert.fail("Should have triggered InvalidOperationException");
		}
		catch(Exception e) {
			Assert.assertTrue(e instanceof InvalidOperationException);
			Assert.assertEquals(INVAL_OP_MSG,e.getMessage());
		}
	}
	
	@Test
	public void operationInstanciationValid() throws InvalidOperationException {
		Operation op = new Operation(account, 20f, OperationType.DEPOSIT);
		Assert.assertEquals(account, op.targetAccount());
		Assert.assertEquals(Float.valueOf(20), op.amount());
		Assert.assertEquals(OperationType.DEPOSIT, op.type());
	}
	
	// Operation execution
		@Test
		public void operationDepositExecution() throws InvalidOperationException, InsufficientFundsException {
			Account account = Mockito.mock(Account.class);
			ArrayList<Operation> history = new ArrayList<>();
			
			Mockito.when(account.balance())
				.thenReturn(500f);
			Mockito.when(account.operations())
				.thenReturn(history);

			Operation op = new Operation(account, 20f, OperationType.DEPOSIT);
			op.execute();
			
			Assert.assertEquals(Float.valueOf(520f), op.newBalance());
			Assert.assertEquals(history.get(0), op);
			Assert.assertNotNull(op.date());
			Mockito.verify(account).modifyBalance(520f);
			
		}
		
		@Test
		public void operationWithdrawalExecution() throws InvalidOperationException, InsufficientFundsException {
			Account account = Mockito.mock(Account.class);
			ArrayList<Operation> history = new ArrayList<>();
			
			Mockito.when(account.balance())
				.thenReturn(500f);
			Mockito.when(account.operations())
				.thenReturn(history);

			Operation op = new Operation(account, 20f, OperationType.WITHDRAWAL);
			op.execute();
			
			Assert.assertEquals(Float.valueOf(480f), op.newBalance());
			Assert.assertEquals(history.get(0), op);
			Assert.assertNotNull(op.date());
			Mockito.verify(account).modifyBalance(480f);
		}
		
		@Test
		public void operationWithdrawalExecutionInsufficientFunds() throws InvalidOperationException, InsufficientFundsException {
			Account account = Mockito.mock(Account.class);
			ArrayList<Operation> history = new ArrayList<>();
			
			Mockito.when(account.balance())
				.thenReturn(500f);
			Mockito.when(account.operations())
				.thenReturn(history);

			Operation op = new Operation(account, 550f, OperationType.WITHDRAWAL);
			try {
				op.execute();
				Assert.fail("Should have triggered InsufficientFundsException");
			}
			catch(Exception e) {
				Assert.assertTrue(e instanceof InsufficientFundsException);
				Assert.assertEquals(
						"Trying to withdraw 550,00 but current account balance is only 500,00",
						e.getMessage()
						);
				Mockito.verify(account, never()).modifyBalance(Mockito.any());
				Assert.assertTrue(history.isEmpty());
			}
		}
	

}
