package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menu {
	private Map<String, Food> menu;
	
	public Menu(){
		this.menu = new HashMap<String, Food>();
		try {
			processMenuData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void processMenuData() throws FileNotFoundException {
		InputStream in = getClass().getResourceAsStream("/MenuData.txt");
	    if (in == null) {
	        throw new FileNotFoundException("Could not find albums.txt");
	    }
	
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            // Each line: <itemName>,<type>,<price>
	            String[] temp = line.strip().split(",");
	            String itemName = temp[0];
	            String type = temp[1];
	            
	            // Converting the string to enum type
	            FoodType typeVal = FoodType.valueOf(type.toUpperCase());
	            
	            double price = Double.parseDouble(temp[2]);
	
	            	
	            // adding each item to the menu
	            addMenuItem(itemName, typeVal, price);
	        }
	    } catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addMenuItem(String itemName, FoodType type, double price){
			// Create the Food instance
			Food item = new Food(itemName, type, price);
			
			// Add the Food instance to the menu hashmap
			menu.put(itemName.toUpperCase(), item);
	}
	
	// Method to get menu items so we can process it in the view
	public ArrayList<Food> getMenuItems() {
		return new ArrayList<>(menu.values());
	}
	
	// Method to get a single Food object from the menu
	public Food getItemFromMenu(String foodName) {
		return menu.get(foodName.toUpperCase());
	}
	
	
}
