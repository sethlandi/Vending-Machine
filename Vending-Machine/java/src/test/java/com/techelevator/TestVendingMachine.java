package com.techelevator;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TestVendingMachine  {

	VendingMachine test = new VendingMachine();
	
	@Test
	public void Test_Key_B2_contains_correct_values() {
		assertEquals("Constructor did not scrape inventory correctly", "Cowtales", test.getName("B2"));
		assertEquals("Constructor did not scrape inventory correctly", 1.50, test.getPrice("B2"), 0);
		assertEquals("Constructor did not scrape inventory correctly", "Candy", test.getType("B2"));
		assertEquals("Constructor did not scrape inventory correctly", 5, test.getCount("B2"));
	}

	@Test
	public void Test_balances_update() throws IOException {
		test.enterMoney("5");
		test.chooseSnack("B2");
		test.vend();
		assertEquals("Balances did not update properly", 3.50, test.getBalance(), 0);
	}
	@Test
	public void test_vend_balance_too_low() throws IOException {
		test.enterMoney("1");
		test.chooseSnack("B2");
		test.vend();
		assertEquals("Machine should not vend", 1.50, test.getAmountOwed(), 0);
	}
	@Test
	public void test_item_vends() throws IOException {
		test.enterMoney("5");
		test.chooseSnack("A1");
		test.vend();
		assertEquals("Machine did not update balance properly", 1.95, test.getBalance(), 0.00001);
		assertEquals("Machine did not update internal bank properly", 3.05, test.getVmBank(), 0);
	}
	@Test
	public void test_coin_return() throws IOException {
		test.enterMoney("5");
		test.chooseSnack("A1");
		test.vend();
		test.coinReturn();
		assertEquals("Test did not return correct number of quarters", 7, test.getQuarters());
		assertEquals("Test did not return correct number of Dimes", 2, test.getDimes());
		assertEquals("Test did not return correct number of Nickels", 0, test.getNickels());
		
	}
	
	
}
