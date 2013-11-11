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
enum PersonState {Idle, WantsToGoHome, WantFood, CookHome, WaitingForCooking, GoOutEat, StartEating, Eating, NeedsToWork, Working,    };
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
if personState == DoneWorking {
	
}
evaluateStatus();
return false;
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
	if hasWorked = false {
		personState = NeedsToWork;
		stateChanged();
	}
	else {
		personState = Idle;
		stateChanged();
		//Go to Evaluate status

	}
}

```

```
msgGoWork() { //Not Really used. Can be used for hacks.
	personState = NeedsToWork;
	stateChanged();
}

```

```
msgWorkDone() {
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
msgAtHome() {
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
public void
```
```
public void DoGiveGroceyOrder(){
	myWorker.msgGetGroceries(myGroceries);

}
```
```
public void DoPay() {
	myWorker.HereIsMoney(myBill.price);

}
```
```
public void DoLeaveMarket() {


}
```
