package com.societe.generale.bank.account.kata;

import java.util.Date;

import org.junit.Assert;

import com.societe.generale.bank.account.kata.exception.InsufficientFundsException;
import com.societe.generale.bank.account.kata.exception.InvalidOperationException;
import com.societe.generale.bank.account.kata.model.Account;
import com.societe.generale.bank.account.kata.model.Client;
import com.societe.generale.bank.account.kata.model.Operation;
import com.societe.generale.bank.account.kata.model.OperationType;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;




public class OperationsHistorySteps {

	private Account account;
	private Float depositAmount;
	private Float withdrawnAmount;
	private Date dateBefore;

	// Deposit once
	@Given("The newly created bank account of client Paul Guillemet")
	public void newly_created_bank_account() {
	    this.account = new Account(new Client("Paul", "Guillemet"));
	}

	@When("I deposit {float} then withdraw {float}")
	public void i_deposit_then_withdraw(float amount, float amount2) throws InsufficientFundsException, InvalidOperationException {
		this.depositAmount = amount;
		this.withdrawnAmount = amount2;
		this.dateBefore = new Date();
		this.account.deposit(depositAmount);
		this.account.withdraw(withdrawnAmount);
	}

	@Then("The account history contains 2 operations with the correct data")
	public void history_is_correct() throws Throwable {
	    Assert.assertEquals(2, this.account.operations().size());
	    Operation deposit = this.account.operations().get(0);
	    Operation withdrawal = this.account.operations().get(1);
	    
	    Assert.assertEquals(OperationType.DEPOSIT, deposit.type());
	    Assert.assertFalse(deposit.date().before(dateBefore));
	    Assert.assertEquals(depositAmount, deposit.amount());
	    Assert.assertEquals(depositAmount, deposit.newBalance());
	    
	    Assert.assertEquals(OperationType.WITHDRAWAL, withdrawal.type());
	    Assert.assertFalse(withdrawal.date().before(deposit.date()));
	    Assert.assertEquals(withdrawnAmount, withdrawal.amount());
	    Assert.assertEquals(
	    		Float.valueOf(depositAmount - withdrawnAmount), 
	    		withdrawal.newBalance()
	    	);
	}
	

}
