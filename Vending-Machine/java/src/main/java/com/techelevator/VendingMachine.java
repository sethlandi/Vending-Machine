package com.techelevator;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map.Entry;

public class VendingMachine  {
   
	
	private double balance = 0;
    private double amountOwed;
    private String userEntry;
    private int quarters;
	private int dimes;
	private int nickels;
	private double vmBank = 0;
	private Inventory stock;
	private boolean validSnack = false;
	 private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	String log = "log.txt";
	File logFile = new File(log);
	
	
	DecimalFormat df = new DecimalFormat("0.00");
	
	
	
	public VendingMachine() {
		stock = new Inventory();
		
	}
		 
     
    public void enterMoney(String cash) throws IOException {
    	cash = cash.toLowerCase();
    	double cashValue = 0;
    	if (cash.contains("10") || cash.contains("ten")) {
    		cashValue = 10;
    	}
    	else if (cash.contains("1") || cash.contains("one")) {
    		cashValue = 1;
    	}
    	else if (cash.contains("2") || cash.contains("two")) {
    		cashValue = 2;
    	}
    	else if (cash.contains("5") || cash.contains("five")) {
    		cashValue = 5;
    	}
    	else {
    		System.out.println();
    		System.out.println("Please enter valid bill");
    		System.out.println();
    	}
    	
    	balance += cashValue;
    	System.out.println();
    	System.out.println("Current balance: $" + df.format(balance));
    	System.out.println();
    	Timestamp time = new Timestamp(System.currentTimeMillis());
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
    		
    		writer.append(time + " " + "FEED MONEY: $" + df.format(cashValue) + " $" + df.format(balance) + "\n");
    		writer.close();
    	}
    }
    
   
      public void chooseSnack(String userEntry) {
	    if (stock.getName().containsKey((userEntry))) {
	    	amountOwed = stock.getKeyPrice(userEntry);
	    	this.userEntry = userEntry;
	    	System.out.println("Item chosen: " + stock.getKeyName(userEntry) + ", price: $" + df.format(stock.getKeyPrice(userEntry)));
	    	System.out.println();
	    	validSnack = true;
	    }
	    if(!stock.getName().containsKey((userEntry))) {
	   
	    	System.out.println("Please choose a enter a valid key");
	    	
	    	
	    } 
    }
    
      public void coinReturn() throws IOException {
    	double oldBalance = balance;
    	quarters = (int) (balance / 0.25);
    	balance = balance - (quarters * .25);
    	dimes = (int) (balance / 0.10);
    	balance = balance - (dimes * 0.10);
    	nickels = (int) (balance / 0.05);
    	balance = balance - (nickels * 0.05);	
    	
    	System.out.print("Your Change is: ");
    	if (quarters == 1) {
    		System.out.print("1 Quarter");
    	}
    	if (quarters > 1) {
    		System.out.print(quarters + " Quarters");
    	}
    	if (dimes == 1) {
    		System.out.print(" 1 dime");
    	}
    	if (dimes > 1 ) {
    		System.out.print(" " + dimes + " dimes");
    	}
    	if (nickels == 1) {
    		System.out.print(" 1 nickel");
    	}
    	if (nickels > 1 ) {
    		System.out.print(" " + nickels + " nickel");
    	}
    	System.out.println();
    	Timestamp time = new Timestamp(System.currentTimeMillis());
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
    	
    		writer.append(time + " " + "GIVE CHANGE: " + " " + userEntry + " $" + df.format(oldBalance) + " $" + df.format(balance) + "\n");
    		writer.close();
    	}
    }
    
    
	private void getSnack() {
    	if (stock.getKeyType(userEntry).equals("Chip")) {
    		System.out.print("Crunch crunch, enjoy your " + stock.getKeyName(userEntry));
    	}
    	if (stock.getKeyType(userEntry).equals("Candy")) {
    		System.out.print("Munch Munch, enjoy your " + stock.getKeyName(userEntry));
    	}
    	if (stock.getKeyType(userEntry).equals("Gum")) {
    		System.out.print("Chew Chew, enjoy your " + stock.getKeyName(userEntry));
    	}
    	if (stock.getKeyType(userEntry).equals("Drink")) {
    		System.out.print("Glug Glug, enjoy your " + stock.getKeyName(userEntry));
    	}
    }
       
    public void vend() throws IOException {
    	Timestamp time = new Timestamp(System.currentTimeMillis());
    	if (balance >= amountOwed && stock.getKeyCount(userEntry) > 0) {
    		
    		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
    			
    				writer.append(time + " " + stock.getKeyName(userEntry) + " " + userEntry + " $" + df.format(balance) + " $" + df.format(balance - amountOwed) + "\n");
        	}
    		
    		balance = balance - amountOwed;
    		getSnack();
    		System.out.println(", Your balance is $" + df.format(balance));
    		stock.setKeyCount(userEntry, stock.getKeyCount(userEntry) - 1);
    		vmBank += amountOwed;
    		
    		
    	}
    	
    	else if (stock.getKeyCount(userEntry) == 0) {
    		
    		System.out.println("Item Sold Out");
    		
    	}
    	
    	else if (balance < amountOwed) {
    		
    
    		System.out.println("Please feed me more money.  you owe: $" + df.format((amountOwed - balance)));
    	
    	}
    	
    	else {
    		
    		System.out.println("You didn't select anything");
    		
    	}
    }
    
    public void generateSalesReport() throws FileNotFoundException {
    	Timestamp time = new Timestamp(System.currentTimeMillis());
    	String salesReport = time + "_SalesReport.txt";
		File sales = new File(salesReport);
		PrintWriter print = new PrintWriter(salesReport);
			for(Entry<String, String> snack : stock.getName().entrySet()) {
			
				String name = snack.getValue();
				int sold = 5 - stock.getKeyCount(snack.getKey());
				print.println(name +" "  + "|" + " " + sold);
				}
			print.print("Total Sales $" + df.format(vmBank));
			print.close();
			System.out.println("Sales report generated, Thank you");
    }

	public double getBalance() {
		return balance;
	}
	public double getAmountOwed() {
		return amountOwed;
	}
	public String getName(String key) {
		return stock.getKeyName(key);
	}

	public double getPrice(String key) {
		return stock.getKeyPrice(key);
	}

	public String getType(String key) {
		return stock.getKeyType(key);
	}

	public int getCount(String key) {
		return stock.getKeyCount(key);
	}

	public double getVmBank() {
		return vmBank;
	}

	public int getQuarters() {
		return quarters;
	}

	public int getDimes() {
		return dimes;
	}

	public int getNickels() {
		return nickels;
	}
	public Inventory getStock() {
		return stock;
	}
	public boolean isValidSnack() {
		return validSnack;
	}
    
}
