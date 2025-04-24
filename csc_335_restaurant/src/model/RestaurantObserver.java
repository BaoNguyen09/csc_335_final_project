package model;

public interface RestaurantObserver {
	void assignServerEvent(String serverName, int tableNum);
	
	void assignGroupEvent(int groupId, int tableNum);
	
	void removeServerEvent(int tableNum);
}
