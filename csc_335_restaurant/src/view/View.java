package view;

import javax.swing.SwingUtilities;

import controller.Controller;
import model.Restaurant;


public class View {

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Controller controller = new Controller(restaurant);
        
        // Create the UI
        SwingUtilities.invokeLater(() -> { 
        	RestaurantUI RestaurantUI = new RestaurantUI(controller);
        	restaurant.addRestaurantObserver(RestaurantUI);
            
            RestaurantUI.setVisible(true);
        });
    }
}