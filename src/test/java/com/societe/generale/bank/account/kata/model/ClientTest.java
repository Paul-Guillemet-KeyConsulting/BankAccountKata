package com.societe.generale.bank.account.kata.model;

import org.junit.Assert;
import org.junit.Test;

public class ClientTest {

	@Test
	public void shouldNotBeNullWhenInstanciated() {
		Client client  = new Client();
		Assert.assertNotNull(client);
	}

}
