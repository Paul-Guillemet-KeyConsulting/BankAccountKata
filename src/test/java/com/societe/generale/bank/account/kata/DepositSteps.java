package com.societe.generale.bank.account.kata;

import org.junit.Assert;

import com.societe.generale.bank.account.kata.exception.InsufficientFundsException;
import com.societe.generale.bank.account.kata.exception.InvalidOperationException;
import com.societe.generale.bank.account.kata.model.Account;
import com.societe.generale.bank.account.kata.model.Client;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;




public class DepositSteps {

	private Account account;

	// Deposit once
	@Given("The newly created bank account of a client named Paul Guillemet")
	public void newly_created_bank_account() {
	    this.account = new Account(new Client("Paul", "Guillemet"));
	}

	@When("I deposit {float}")
	public void i_deposit(float amount) throws InsufficientFundsException, InvalidOperationException {
		this.account.deposit(amount);
	}

	@Then("The account balance is {float}")
	public void the_balance_is(float amount) throws Throwable {
	    Assert.assertEquals(Float.valueOf(amount), this.account.balance());
	}
	
	// Deposit twice
	@Given("An other newly created bank account of a client named Paul Guillemet")
	public void other_newly_created_bank_account() {
	    this.account = new Account(new Client("Paul", "Guillemet"));
	}

	@When("I deposit {float} and then {float} more")
	public void i_deposit_twice(float amount, float amount2) throws InsufficientFundsException, InvalidOperationException {
		this.account.deposit(amount);
		this.account.deposit(amount2);
	}

	@Then("The new account balance is {float}")
	public void the_new_balance_is(float amount) throws Throwable {
	    Assert.assertEquals(Float.valueOf(amount), this.account.balance());
	}

}
