# CSC 335 Final Project: Restaurant

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
