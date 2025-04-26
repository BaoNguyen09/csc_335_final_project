package model;

public interface RestaurantObserver {
	// Adding server and group
	void onGroupUpdate();
	void onServerUpdate();
	void onTableUpdate();
	
	// Assigning server and group to table
	void assignServerEvent(String serverName, int tableNum);
	void assignGroupEvent(int groupId, int tableNum);
	
}

