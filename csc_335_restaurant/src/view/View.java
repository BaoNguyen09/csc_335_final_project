package view;

import java.awt.Menu;

import javax.swing.SwingUtilities;

import model.Restaurant;
import model.Table;

public class View {

	public static void main(String[] args) {
		// Our main restaurant that is going to be changed
		Restaurant restaurant = new Restaurant();
				
        SwingUtilities.invokeLater(() -> new RestaurantUI(restaurant).setVisible(true));


	}

}
