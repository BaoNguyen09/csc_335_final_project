package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                double price = Double.parseDouble(temp[2]);

                	
                // adding each item to the menu
                addMenuItem(itemName, type, price);
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addMenuItem(String itemName, String type, double price){
			// Create the Food instance
			// Add the Food instance to the menu hashmap
		
			//System.out.println(itemName + type + String.valueOf(price));
	}
	
	public Food getFoodItem(String itemName) {
		return menu.get(itemName);
		
	}
}
