package model;

/* 
 * This interface is used to expose only methods that controller needs to use
 * to let a customer order food without exposing full Customer API
 */

public interface OrderFood {
	String getName();
	boolean orderFood(Food food, int quantity, String mods);
}
