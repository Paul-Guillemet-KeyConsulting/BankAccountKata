package com.societe.generale.bank.account.kata.model;

import org.junit.Assert;
import org.junit.Test;

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
	

}
