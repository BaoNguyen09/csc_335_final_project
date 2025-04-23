package model;

public interface RestaurantObserver {
	void assignServerEvent(Server s, int tableNum);
	
	void assignGroupEvent(int groupId, int tableNum);
	
	void removeServerEvent(Server s, int tableNum);
}
