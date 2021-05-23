package com.societe.generale.bank.account.kata;

import org.junit.Assert;

import com.societe.generale.bank.account.kata.exception.InsufficientFundsException;
import com.societe.generale.bank.account.kata.exception.InvalidOperationException;
import com.societe.generale.bank.account.kata.model.Account;
import com.societe.generale.bank.account.kata.model.Client;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;




public class WithdrawalSteps {

	private Account account;

	// Withdraw when there is enough on the account
	@Given("A newly created bank account where i made a {float} deposit")
	public void newly_created_bank_account(float amount) throws InsufficientFundsException, InvalidOperationException {
	    this.account = new Account(new Client("Paul", "Guillemet"));
	    this.account.deposit(amount);
	}

	@When("I withdraw {float}")
	public void i_deposit(float amount) throws InsufficientFundsException, InvalidOperationException {
		this.account.withdraw(amount);
	}

	@Then("The balance is {float}")
	public void the_account_balance_is(float amount) throws Throwable {
	    Assert.assertEquals(Float.valueOf(amount), this.account.balance());
	}
	
	// Withdraw when there is not enough on the account
	@Given("A newly created bank account where i only made a {float} deposit")
	public void other_newly_created_bank_account(float amount) throws InsufficientFundsException, InvalidOperationException {
	    this.account = new Account(new Client("Paul", "Guillemet"));
	    this.account.deposit(amount);
	}

	@When("I try to withdraw {float}")
	public void i_try_to_withdraw(float amount) throws InsufficientFundsException, InvalidOperationException {
		try {
			this.account.withdraw(amount);
		}
		catch(Exception e) {}
	}

	@Then("The balance is still {float}")
	public void the_new_balance_is_still(float amount) throws Throwable {
	    Assert.assertEquals(Float.valueOf(amount), this.account.balance());
	}

}
