package view;

import java.awt.Menu;

import javax.swing.SwingUtilities;

import model.Restaurant;
import model.Table;

public class View {

	public static void main(String[] args) {
		Restaurant restaurant = initializeRestaurant();
		Menu menu = new Menu();
		
		
        SwingUtilities.invokeLater(() -> new RestaurantUI().setVisible(true));


	}

	private static Restaurant initializeRestaurant() {
		Restaurant restaurant = new Restaurant();

        // Table(int tableNum, int capacity)
//        restaurant.addTable(new Table(1, 10));
//        restaurant.addTable(new Table(2, 10));
//        restaurant.addTable(new Table(3, 4));
//        restaurant.addTable(new Table(4, 4));
//        restaurant.addTable(new Table(5, 2));
//        restaurant.addTable(new Table(6, 2));

	
		return restaurant;
	}
}
