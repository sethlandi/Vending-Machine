package com.techelevator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.Scanner;

/**************************************************************************************************************************
*  This is your Vending Machine Command Line Interface (CLI) class
*
*  THIS is where most, if not all, of your Vending Machine interactions should be coded
*  
*  It is instantiated and invoked from the VendingMachineApp (main() application)
*  
*  Your code should be placed in here
***************************************************************************************************************************/
import com.techelevator.view.Menu; // Gain access to Menu class provided for the Capstone

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "1) Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "2) Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "3) Exit";
	private static final String MAIN_MENU_OPTION_REPORT = "";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_REPORT };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "1) Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "2) Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "3) Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	private Menu vendingMenu; // Menu object to be used by an instance of this class

	private VendingMachine machine;

	public VendingMachineCLI(Menu menu) throws FileNotFoundException { // Constructor - user will pas a menu for this class to use
		this.vendingMenu = menu;
		machine = new VendingMachine();

	
	
	  
	}

	public void run() throws IOException {
		
		Scanner keyboard = new Scanner(System.in); 
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		boolean shouldProcess = true;

		while (shouldProcess) {

			String choice = (String) vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS); // Display menu and get choice

			switch (choice) {

			case MAIN_MENU_OPTION_DISPLAY_ITEMS:
				for(Entry<String, String> snack : machine.getStock().getName().entrySet()) { // invoke method to display items in Vending Machine
					String key  = snack.getKey();
					String name = snack.getValue();
					String price = df.format(machine.getPrice(snack.getKey()));
					String quantity;
					if (machine.getCount(snack.getKey()) == 0) {
						quantity = "Sold Out";
					}
					else {
						quantity = Integer.toString(machine.getCount(snack.getKey()));
					}
					 
					System.out.printf("%s: %-12s\t Price: $%s\t Quanitity: %s\n", key, name, price, quantity);
					
				}
				
				break;

			case MAIN_MENU_OPTION_PURCHASE:
				boolean bool = true;
				while (bool) {
					String secondChoice = (String) vendingMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					switch (secondChoice) {

					case PURCHASE_MENU_OPTION_FEED_MONEY:
						String option;
						
						do {
							System.out.println();
							System.out.println("Feed me money");
							String cash = keyboard.nextLine();
							machine.enterMoney(cash);
							System.out.println("Enter more?(y/n)");
							option = keyboard.nextLine();
						} while (option.equals("y"));

						break;

					case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
						
							System.out.println("Select an item");
							String response = keyboard.nextLine();
							machine.chooseSnack(response);
							
						if (machine.isValidSnack()) { 	
							machine.vend();
						}

						break;

					case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
						machine.coinReturn();
						bool = false;
						break;
					}
				}

			
				break;
				
			case MAIN_MENU_OPTION_EXIT:
				endMethodProcessing(); 
				shouldProcess = false; 
				break;
				
			case MAIN_MENU_OPTION_REPORT:
				machine.generateSalesReport();
				shouldProcess = false; 
				break;
			} 
		}

		
		keyboard.close();
	}

	
	public static void displayItems() { 
	

	}

	public static void purchaseItems() { 
		

	}

	public static void endMethodProcessing() { 
		
		System.out.println("Thank you and have A wonderful day!");
	}
}
