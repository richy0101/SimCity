#Design Doc: MarketCustomer

##Data
```
List<Roles> roles;
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
private class PersonTimerTask extends TimerTask {};



```
	
##Scheduler
```
if there exists a Role r in roles, such that r.active() {
	then exectue active role's rules
	return true;
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
	Deactivate current active role;
	activate transportation role with restaurant as destination;
	gui.GoRestaurant//;

	//Where does the logic for activate Restaurant customer role go?

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
