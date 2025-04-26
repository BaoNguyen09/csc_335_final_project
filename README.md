# CSC 335 Final Project: Restaurant
# 🍽️ Restaurant Management System

This project is a **Restaurant Management System** designed using **Java Swing** for the UI and follows the **Model-View-Controller (MVC)** architecture. The system allows servers to manage tables, take group orders, assign servers, handle billing, and track sales performance in real-time.

## General Requirements.  
Implement an app for managing orders in a restaurant from the perspective of a server.  
- Assign tables to a server.
- Input orders for each person at a table.
- The app should include menu items, organized by different categories, such as entrees, 
drinks, desserts, etc. Costs should also be included.
- Some menu items may allow for modifications, and that should be implemented.
- Should include functionality for calculating: 
  - the bill 
  - splitting the bill 
    - by evenly splitting it between all the people at the table 
    - by individual orders 
- Closing an order when the bill is paid – closed orders should still be maintained 
- When a bill is paid, customers have the option of adding a tip and a server’s tips should 
be tracked throughout their shift 
- Implement functionality for managing multiple servers. 
- Implement functionality for tracking sales of specific menu items. 
    - Sort sales based on most frequently ordered items. 
    - Sort sales based on the total amount of money made from a specific item – i.e. 
the cost per item * the number of items sold 
- Determine the server who earned the most from tips.  
## Ideas to Increase Complexity: 
- Implement a high-quality GUI using the OBSERVER pattern.  
- Add additional functionality to the backend.  
- Do more data analysis from the perspective of the restaurant owner – e.g. # of people, 
busiest times, etc.

---

## 🚀 How to Run the Code

### Prerequisites:
- JavaSE-23 JRE [23.0.2]
- Junit 5
- An IDE like IntelliJ IDEA, Eclipse, or VSCode (optional but recommended)
- Git (if cloning the repository)

### Steps:

1. **Clone the Repository**
- `git clone https://github.com/BaoNguyen09/csc_335_restaurant.git`
- `cd csc_335_restaurant`

2. **Compile the Project**
- If using terminal: `javac -d bin src/**/*.java`

3. **Run the Application**
- From the `bin` directory: `java view.MainUI`

> Ensure `View.java` is your main class that launches the Swing interface.

4. **Using an IDE**
- Import the project as a Java project.
- Set `View.java` (or your designated main class) as the run configuration.
- Click **Run**.

---

## 🛠️ Project Design Overview

The system is organized into three primary packages: model, view, and test, following the MVC (Model-View-Controller) architecture for clean separation of concerns.

1️⃣ Model (model package)

The Model holds the core business logic and data structures:
	- Restaurant, Server, Group, Table: Represent the real-world restaurant system.
	- Menu, Food, FoodData, FoodType: Represent menu items and customer orders.
	- Bill: Manages food orders and bill processing.
	- Sales: Tracks quantity sold and revenue by item, and notifies views via the Observer pattern.
	- Observer interfaces: RestaurantObserver, SalesObserver.

2️⃣ View (view package)

Implements the UI using Java Swing:
	- `RestaurantUI.java`: The main GUI window that shows the restaurant tables and calls the other views.
	- `OrderingUI.java`: Allows servers to take and customize orders.
	- `SalesUI.java`: Displays sales statistics with sortable tables.
	- `TableBox.java`: represents the tables in the restaurantUI
 	- `View.java`: Support UI layout and interactions.

3️⃣ Test (test package)

Contains unit tests for core components:
	- `SalesTests.java`: Validates revenue and quantity-based sorting logic.
	- Other test classes: BillTests.java, CustomerTests.java, FoodTests.java, MenuTests.java, etc., ensure correctness of each module.
	- Tests are written using JUnit and test functionality independently of the UI.
 	- Make sure to have Junit 5

⸻

🔄 Design
- MVC Architecture for modular separation.
 
- Observer Pattern for UI updates triggered by model changes (e.g., sales updates).

 Explainations of the observer pattern we used and how we kept encapsulation:
[  Observer and Interfaces
](https://docs.google.com/document/d/1B3I-wGEAK5AjcK2Ocl2y4GBIX2_unNQAnRmIXSU97OI/edit?usp=sharing) 

We also kept good encapsulation of the Food class by making it immutable and having the mutable items be
inside the FoodData class instead so that we can pass the Food object around without worrying about escaping
references.

- Data structures
  We mostly used hashmaps throughout our code for anything that required fast lookups because
  this would allow us to find things (Ex. group by groupId) in O(1) time instead of O(n) time.

- Sales Sorting
  We wanted to use comparator interface inside FoodData to implement the sortOffRevenue and
  sortByQuantity, however, we found that because we were storing Food in Sales, this made it
  impossible to actually sort using a comparator from FoodData. This we have to manually make
  the comparator inside the collections.sort method.

- Composition
  Because the controller is always interacting with the restaurant class only, we
  made sure that the restaurant class had everything it needed (waitlist, menu, sales, etc.)
  so that we can retreieve all information related to the restaurant instance.
  
- Encapsulation
  We made sure to have copy constructors throughout our code so that the item
  being passed is not an escaping reference. Even in returning things to the controller
  from restaurant we are only passing copies, which is why the view has to update using
  observer everytime a change occurs.
  
- Avoidance of antipatterns
  - PRIMITIVE OBSESSION: We used an enum for the FoodType instead of a string to avoid this
  antipattern.

- Input validation
  - We used design by contract in the backend as our input validation because mostly we will be the ones
   using these functions so there is a lower chance that something will crash the program

  - In the frontend we used statements to check for errors and handle nulls. This will directly prompt
    the user to enter the correct value so that the program doesn't crash. For instance, when adding a group
    we prompt user for number of people in the group and if they enter a not integer or it is < 0 we let the
    user know that the input is not valid.

⸻

🔄 Use of AI-generated code

We used AI mostly when creating the GUI using swing. We did not have extensive experience with swing
and did not have time to make the GUI pretty, so we generated this using AI. We wrote all functions
related to the buttons, notifying observers, and controller methods. AI was only used to design the frontend.


⸻
