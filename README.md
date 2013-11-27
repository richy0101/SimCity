team02
======

SimCity201 Project Repository for CS 201 students

Running Normative Scenario
 * Create a person works. 
 * Can only instantiate people who take the bus as their transportation method. 
 * Initial funds sets their starting money. 
 * Aggressiveness sets their personality for how long they work and sleep.
 * When selecting occupation, market 2 seller does not work. 
 * Market Role is hardcoded. Do not assign person as Market Role job. 
 * Only one restaurant.
 * Multiple people live in homes.
 * All guis for restaurants (other than functional Ryan Stack's designated restaurant) is using market GUI.
 * Potential inconsistencies w/ different computers 
 * (Ex. Busses sometimes stop stopping after running a long time on Mac computers)

Missing functionality in V1
 * We do not have two markets.
 * No intersections.
 * No cars.
 * No collision detection.
 * No A*
 * No apartments.
 * Populate City does not work.
 * No animation for markets to deliver.
 * No walking as sole form of transportation.
 * No bank robberies.
 * No weekends.

Ryan Stack's Contribution:
 * Implemented restaurant with added shared data, market interactions, and payment from cashier
 * Implemented front-end GUI, created window and application, and actionListeners
 * Created graphics for city, restaurant, bank, and some agents (with help from Ryan Sheh)
 * Helped implement bank, bankGUI, and roles for bank
 * Created an XML reader to read in scripts for creating scenarios and populating the city (with help from Alex Huang)
 * Created initial setup for project with interfaces and stubs from all designs
 * Created initial global clock and role class (iterations followed from other team members)
 * Missing functionality for V2: depositing excess money in bank

Richard Phillip's Contribution:
 * Implementation for bank roles
 * Implementation for bank GUI
 * Extensive unit testing for bank norm and some non norm scenarios
 * Missing functionality for V2: no robber interaction, no database (EC)

Reid Nakamura's Contribution:
 * Implemented Market Roles
 * Implemented Market GUI
 * Implement Market Role Unit Test
 * Helped to debug Transportation GUI
 * Resolved issue with Ryan Sheh to set up CardLayout of building animation panels

Ryan Sheh's Contribution
 * Main Window GUI (w/ help from Ryan Stack)
 * Bank GUI (w/ Richard & Ryan Stack )
 * Market GUI (w/ Reid)
 * Home GUI (w/ Alex)
 * Implemented CardLayout (w/ help from Reid)
 * Implemented Directory Class for References (w/ help from Alex)
 * Unit Testing Bank Roles (w/ Richard)
 * Helped Implement Bus Agent & GUI (w/ Ben Tan)
