package com.techelevator;



	import java.io.File;
	import java.io.FileNotFoundException;
	import java.util.Map;
	import java.util.Scanner;
	import java.util.TreeMap;
	 
public class Inventory {
	 
	private Map<String,Double> price = new TreeMap<>();
	private Map<String,String> type = new TreeMap<>();
	private Map<String,Integer> count = new TreeMap<>();
	private Map<String,String> name = new TreeMap<>();
	     

	public Inventory() {
		inventory();
	}
	  

	private void inventory() {
		
	    try {
	    	Scanner scanner = new Scanner(new File("vendingmachine.csv"));
	           
	    	while(scanner.hasNextLine()) {
	    		String fileLine = scanner.nextLine();
	               
	    		String[] index = fileLine.split("\\|");
	               
	    		name.put(index[0],index[1]);
	    		price.put(index[0],Double.parseDouble(index[2]));
	    		type.put(index[0],index[3]);
	    		count.put(index[0], 5);
	    	}
	    	scanner.close();
	    	
	    } catch (FileNotFoundException e) {
	            e.printStackTrace();
	    }   
	}
	
	public String getKeyName(String key) {
		return name.get(key);
	}

	public double getKeyPrice(String key) {
		return price.get(key);
	}

	public String getKeyType(String key) {
		return type.get(key);
	}

	public int getKeyCount(String key) {
		return count.get(key);
	}
		
	public void setKeyCount(String key, int update) {
		count.replace(key, update);
	}
	    
	public Map<String, Double> getPrice() {
		return price;
	}

	public Map<String, String> getType() {
		return type;
	}

	public Map<String, Integer> getCount() {
		return count;
	}

	public Map<String, String> getName() {
		return name;
	}		
}
