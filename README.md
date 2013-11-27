team02
======

SimCity201 Project Repository for CS 201 students

How TO play:
 *Run Program
 *Observe hardcoded persons who have a preset beginning scenario
 *Use Create a Person to create people to interact with the city.
 *Read below for limitations and known bugs.

Running Normative Scenario
 * Create a person works. Select None for occupation to create an unemployed person to see how he interacts
 * Can only instantiate people who take the bus as their transportation method. 
 * Initial funds sets their starting money. 
 * Aggressiveness sets their personality for how long they work and sleep.
 * When selecting occupation, market 2 seller does not work. 
 * Market Role1 and 2 have already set up person as its job. Do not reassign them in Create a person.
 * Only one restaurant.
 * Multiple people live in homes.
 * All guis for restaurants (other than functional Ryan Stack's designated restaurant) is using market GUI.
 * Potential inconsistencies w/ different computers 
 * (Ex. Busses sometimes stop stopping after running a long time on Mac computers)

Missing functionality in V1
 * Only one market running.
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
 * Unit testing for bank roles for norm/some non-norm scenarios
 * Unit testing for bank GUI for norm/some non-norm scenarios
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

Alex Huang's Contribution:
 * Designed and implemented Person Agent and PersonGUI
 * Integrated all roles and gui animations with Person Agent(contributions from respective implementers).
 * Debugged all Bank roles and fixed interactions within it as they were broken initially during integration.
 * Fixed Bank Role GUI's for interactions (with help from Stack, Sheh, Phillips).
 * Implemented Directory class and its data(contribution from whole team)
 * Polish superNorm test with help from Reid and Sheh
 * Implemented Transportation Role and TransportationRoleGUI
 * Missing Complete Unit Tests for PersonAgent and PersonGUI because of sharedInstance issues in Directory. Pushing back this req for V2 delivery. 

