package city;


//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.Semaphore;

import agent.Agent;
import city.interfaces.Transportation;

public class TransportationRole extends Agent implements Transportation  {
        int currentStopNumber = 0;
        int desiredStopNumber = 0;
        String destination;
        CarAgent car;
        BusAgent bus;
        Boolean hasCar = false;
        
        public enum TransportationState 
                {Walking, NeedsToTravel, InTransit, AtDestination, None};
                
        TransportationState state = TransportationState.None;

        
        public TransportationRole() {
                super();
        }
        
        void msgThisIsBusStop(int BusStopNumber) {
                //bus
        }
        
        void msgArrivedAtDestination(TransportationRole person, String destination) {
                
                state = TransportationState.AtDestination;
                stateChanged();
        }



        @Override
        protected boolean pickAndExecuteAnAction() {
                // TODO Auto-generated method stub
                return false;
        }
}