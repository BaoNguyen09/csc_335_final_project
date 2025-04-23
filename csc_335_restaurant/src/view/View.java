package view;

import javax.swing.SwingUtilities;

import controller.Controller;
import model.Restaurant;
import model.Table;
import model.Server;
import model.Group;
import model.Customer;

import java.util.ArrayList;

public class View {

    public static void main(String[] args) {
        Restaurant restaurant = initializeRestaurant();
        Controller controller = new Controller(restaurant);
        
        // Create the UI
        SwingUtilities.invokeLater(() -> { 
        	RestaurantUI ui = new RestaurantUI(controller);
            
            // Register the UI as an observer of the restaurant
            //restaurant.addObserver(ui);
            
            ui.setVisible(true);
        });
    }

    private static Restaurant initializeRestaurant() {
        Restaurant restaurant = new Restaurant();

        // intialize tables
//        restaurant.addTable(new Table(1, 10));
//        restaurant.addTable(new Table(2, 10));
//        restaurant.addTable(new Table(3, 4));
//        restaurant.addTable(new Table(4, 4));
//        restaurant.addTable(new Table(5, 2));
//        restaurant.addTable(new Table(6, 2));
//
//        // intialize servers
//        restaurant.addServer(new Server("Alice"));
//        restaurant.addServer(new Server("Bob"));
        //restaurant.addServer(new Server("Charlie"));

        return restaurant;
    }
}