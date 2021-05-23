package com.societe.generale.bank.account.kata.model;

import org.junit.Assert;
import org.junit.Test;

public class ClientTest {

	@Test
	public void clientInstanciation() {
		Client client  = new Client("Paul", "Guillemet");
		Assert.assertEquals("Paul", client.firstName());
		Assert.assertEquals("Guillemet", client.lastName());
	}


}
