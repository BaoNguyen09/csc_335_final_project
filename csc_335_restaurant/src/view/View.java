package view;

import javax.swing.SwingUtilities;

import controller.Controller;
import model.Restaurant;


public class View {

	public static void main(String[] args) {
		// Our main restaurant that is going to be changed
		Restaurant restaurant = new Restaurant();
		Controller controller = new Controller(restaurant);
				
        SwingUtilities.invokeLater(() -> new RestaurantUI(restaurant, controller).setVisible(true));


	}
}

