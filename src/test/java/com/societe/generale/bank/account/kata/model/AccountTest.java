package com.societe.generale.bank.account.kata.model;

import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

	private Client client = new Client("Paul", "Guillemet");

	@Test
	public void accountInstanciation() {
		Account account  = new Account(client);
		Assert.assertEquals(client, account.owner());
	}

	

}
