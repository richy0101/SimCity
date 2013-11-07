#Design Doc: Transportation Agent

##Summary
The Car is initially parked wherever the passenger last left it in carState= Idle. When the passenger needs the Car, he walks to the Car and sends it msgTakeMeHere(destination). The passengerGui then disappears into the Car and the carGui travels to the destination. The destination coordinates are hardcoded in carGui. When the carGui reaches its destination it sends the agent msgAtDestination(), which sends the car scheduler to perform action parkCar. Within parkCar, the Car messages the passenger that it has arrived at its destination, prompting the passenger to bring back its gui at the destination(or drop-off) coordinates.

##Data
int CurrentStopNumber = 0;
int DesiredStopNumber;
enum state TransportationState {NeedsToTravel, InTransit, AtDestination};
	
##Scheduler
	
	if ∃ in TransportationAgent ∋ state.AtDestination()
		then GetOffVehicle(); 
	
	if ∃ in TransportationAgent ∋ state.AtDestination()
		then GetOffVehicle(); 
		
	
		

	
##Messages
	msgThisIsBusStop(int BusStopNumber) {
		print("This is stop number: " + BusStopNumber + ".");
		if(DesiredStopNumber == BusStopNumber) {
			TransportationState = AtDestination;
		}
	}
	
	msgTakeMeHere(String myDestination){ //receives msg from passenger
		carState = inTransit;
		destination= myDestination;
		stateChanged();
	}
	
	msgAtDestination(){ //from carGui when reached destination
		driving.release();
		carState= atDestination;
		stateChanged();
	}

##Actions	
	goTo(String myDestination){
		doGoTo(myDestination); //sets destination in carGui
		driving.acquire(); //to ensure that the gui is uninterrupted on the way
	}
	
	parkCar(){
	//msg gives passenger the destination so its gui can reappear at an appropriate place
		passenger.msgArrivedAtDestination(destination);
		carState= Idle;
	}

	