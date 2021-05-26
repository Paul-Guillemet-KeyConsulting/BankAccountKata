package com.societe.generale.bank.account.kata.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class AmountTest {


	@Test
	public void shouldHaveCorrectValueWhenInstanciated() throws InvalidAmountException {
		BigDecimal value  = new BigDecimal(1);
		
		Amount amount = new Amount(value);
		
		Assert.assertEquals(value, amount.value());
	}
	
	@Test
	public void shouldFailWhenTryingToInstanciateWithNull() {		
		assertThatThrownBy(() -> {
			new Amount(null);
		})
			.isInstanceOf(InvalidAmountException.class)
			.hasMessage("Trying to create an invalid amount");
		
	}
	
	@Test
	public void shouldReturnCorrectValueWhenAdding() throws InvalidAmountException {
		BigDecimal value  = new BigDecimal(2);
		BigDecimal value2  = new BigDecimal(1);
		Amount amount = new Amount(value);
		Amount amount2 = new Amount(value2);
		
		Amount amount3 = amount.add(amount2);
		
		Assert.assertEquals(new BigDecimal(3), amount3.value());
	}
	
	@Test
	public void shouldReturnCorrectValueWhenSubstracting() throws InvalidAmountException {
		BigDecimal value  = new BigDecimal(2);
		BigDecimal value2  = new BigDecimal(1);
		Amount amount = new Amount(value);
		Amount amount2 = new Amount(value2);
		
		Amount amount3 = amount.subtract(amount2);
		
		Assert.assertEquals(new BigDecimal(1), amount3.value());
	}
	
	@Test
	public void shouldReturnTrueWhenUsingIsPositiveWithPositiveAmount() throws InvalidAmountException {
		BigDecimal value  = new BigDecimal(2);
		Amount amount = new Amount(value);
		
		Assert.assertTrue(amount.isPositive());
	}
	
	@Test
	public void shouldReturnFalseWhenUsingIsPositiveWithNegativeAmount() throws InvalidAmountException {
		BigDecimal value  = new BigDecimal(-2);
		Amount amount = new Amount(value);
		
		Assert.assertFalse(amount.isPositive());
	}
	

	

}
