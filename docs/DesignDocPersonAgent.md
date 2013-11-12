<<<<<<< HEAD
﻿#Design Doc: PersonAgent extends Agent
=======
﻿#Design Doc: PersonAgent base
>>>>>>> 12901a47f668103eed93d0db8fb5c6679ea9dc7d

##Data
```
List<Roles> roles;
Map<String, Restaurant> restaurants;
Map<String, Market> markets;
Map<String, Bank> banks;
double funds;
boolean hasCar;
boolean hasWorked;
boolean atHome;
enum HouseState { owns House..ownsAppt..};
HouseState houseState;
enum PersonState {Idle, WantsToGoHome, WantFood, CookHome, WaitingForCooking, GoOutEat, StartEating, Eating, NeedsToWork, Working, InTransit   };
PersonState personState;
int hungerLevel;
Clock clock = clock.sharedInstance();
Timer timer = new Timer();
public class PersonTimerTask extends TimerTask {};
//Restaurant class within person so he knows how to go to a certain restaurant. 
public class Restaurant {
	HostRole hostRole;
	Location location;
	Menu menu;
	RestaurantType type;
	CustomerRole customerRole;
	
	

};

public class Market {
	MarketRole marketRole;
	Location location;
	MarketCustomerRole marketCustomerRole;


};
public class Bank {
	Location location;
	BankTellerRole tellerRole;
	BankManagerRole bankManagerRole;
	BankCustomerRole bankCustomerRole;
	

};
public class Transit {
	Destination;
	TransportationRole transportationRole;
	
}


```
	
##Scheduler
```
if there exists a Role r in roles, such that r.active() {
	then boolean b = r.pickAndExecuteAnAction();
	return b;
}
if personState == WantsToGoHome { 
	goHome();
	return true;
}
if personState == CookHome && atHome == true {
	cookHomeFood();
	return true;
}
if personState == CookHome && atHome == false {
	goHome();
	return true;
}
if personState == GoOutEat {
	goRestaurant();
	return true;
}
if personState == WantFood {
	decideFood(); //change state in decideFood(); and all other actions
	//decides whether to stay home and cook/go home and cook or go to restaurant
	return true;

}
if personState == StartEating {
	eatFood();
	return true;
}
if personState == NeedsToWork {
	goWork();
	return true;
}
return evaluateStatus();
//evaluateStatus becomes our return false for our scheduler of the personAgent.
```

##Messages
```
msgWakeUp() {
	hasWorked = false;
	personState = WantFood;
	stateChanged();

}
```

```
msgCookingDone() {
	personState = startEating;
	stateChanged();

}
```

```
msgDoneEating() {
	personState = Idle;
	hungerLevel = 0;
	stateChanged();
	//Go to Evaluate status

}

```

```
msgGoWork() { //Not Really used. Can be used for hacks.
	personState = NeedsToWork;
	stateChanged();
}

```

```
msgDoneWorking() {
	Deactivate all roles;//not yet sure how to implement.
	personState = WantFood;
	stateChanged();
}

```

```
msgGoHome() {
	personState = WantsToGoHome;
	stateChanged();
}

```

```
msgAtHome() { //GUI message
	atHome = true;
	stateChanged();
}

```
//FILLER MESSAGES FOR FUTURE DESIGNING
```
msg//() {

}

```

```
msg//() {

}

```

##Actions
```
public boolean evaluateStatus() {
	//Intermediate states = Eating, Cooking, Working.
	if personState = Cooking || intermediate states waiting for personal timerTask {
		return false;
	}
	else if hasWorked = false {
		personState = NeedsToWork;
		return true;
	}
	.
	.
	.
	//Needs group designing IMO. This is our algoritm for evaluation outside of norms
}
```
```
public void goHome() {
	Deactivate current active role;
	activate transportation role with home as destination;
	personState = InTransit;
	gui.GoHome//;
}
```
```
public void cookHomeFood() {
	Create new TimerTask {
		msgCookingDone();
	}(Random time for cooking);
	personState = Cooking;

}
```
```
public void goRestaurant() {
	personState = OutToEat;
	Restaurant r = restaurants.chooseOne();
	//
	roles.clear();//deactivates current role;
	guiGoToRestaurant(r.location);
	//We need to figure out how transporation works. If our personAgent takes on a transport role, our scheduler needs to accomodate.
	roles.add(r.cr);
	r.h.msgImHungry(r.cr);

}
```
```
public void decideRestaurant() {
	personState = OutToEat;
	Restaurant r = restaurants.chooseOne();
	//
	roles.clear();//deactivates current role;
	guiGoToRestaurant(r.location);
	//We need to figure out how transporation works. If our personAgent takes on a transport role, our scheduler needs to accomodate with phases for each action...
	roles.add(r.cr);
	r.h.msgImHungry(r.cr);

}
```
```
public void goRestaurant() {
	personState = OutToEat;
	Restaurant r = restaurants.chooseOne();
	//
	roles.clear();//deactivates current role;
	guiGoToRestaurant(r.location);
	//We need to figure out how transporation works. If our personAgent takes on a transport role, our scheduler needs to accomodate.
	roles.add(r.cr);
	r.h.msgImHungry(r.cr);

}
```
```
public void decideFood() {
	//Decide cook or eat out
	if cook {
		personState = CookHome;
	}
	else {
		personState = GoOutEat;
	}
}
```
```
public void eatFood() {
	Create new TimerTask {
		msgDoneEating();
	}(Random time for eating);
	personState = Eating;
}
```
```
public void goWork() {
	hasWorked = true;
	Activate transporation role with work place as destination;
	Create new TimerTask {
		msgDoneWorking();
	}(Random time for Working);
	personState = Working;
	//Where does the logic for activate worker role go?

}
```
//FILLER ACTIONS FOR FUTURE DESIGNING
```
public void // {


}
```
```
public void // {


}
```
