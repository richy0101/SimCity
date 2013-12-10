package restaurant.tanRestaurant.gui;

import restaurant.tanRestaurant.TanCustomerRole;
//import restaurant.HostAgent;
import restaurant.tanRestaurant.TanWaiterRole;
import restaurant.tanRestaurant.TanWaiterRole.MyCustomer;
import gui.Gui;
//import restaurant.gui.CustomerGui.Command;




import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WaiterGui implements Gui {

    private TanWaiterRole agent = null;
    private boolean requestedBreak= false;

    private int xPos = 50, yPos = 50;//default waiter position
    private int xDestination = 292, yDestination = 410;//default start position

    public static final int xTable = 200;
    public static final int yTable = 250;
    
	public static final int xSeat = 70;
	public static final int ySeat = 80;
	
	public enum locationState
	{Away, Entered};
	private locationState locState = locationState.Away;
	
	BufferedImage waiterImage;
    BufferedImage chickenImage;
    BufferedImage pizzaImage;
    BufferedImage saladImage;
    BufferedImage steakImage;
    int waiternumber;
    //RestaurantGui gui;

    public WaiterGui(TanWaiterRole agent) {
        this.agent = agent;
        
        try {
        	waiterImage = ImageIO.read(getClass().getResource("stackRestaurantWaiter.png"));
        	chickenImage = ImageIO.read(getClass().getResource("chicken.png"));
            pizzaImage = ImageIO.read(getClass().getResource("pizza.png"));
            saladImage = ImageIO.read(getClass().getResource("salad.png"));
            steakImage = ImageIO.read(getClass().getResource("steak.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }   
    }

	public WaiterGui(TanWaiterRole w, int n){//RestaurantGui gui, int n){ //HostAgent m) {
		agent = w;
		waiternumber=n;
		xPos = -20;
		yPos = -20;
		if(waiternumber==1){
			xDestination = 140;
			yDestination = 20;
		}
		if(waiternumber==2){
			xDestination = 110;
			yDestination = 20;
		}
		if(waiternumber==3){
			xDestination = 80;
			yDestination = 20;
		}
		if(waiternumber==4){
			xDestination = 50;
			yDestination = 20;
		}
		if(waiternumber==5){
			xDestination = 20;
			yDestination = 20;
		}
		
        try {
        	waiterImage = ImageIO.read(getClass().getResource("stackRestaurantWaiter.png"));
        	chickenImage = ImageIO.read(getClass().getResource("chicken.png"));
            pizzaImage = ImageIO.read(getClass().getResource("pizza.png"));
            saladImage = ImageIO.read(getClass().getResource("salad.png"));
            steakImage = ImageIO.read(getClass().getResource("steak.png"));
        }
        catch(IOException e) {
        	System.out.println("Error w/ Background");
        }   
		//maitreD = m;
		//this.gui = gui;
	}
    
    public void updatePosition() {
        /*if(xPos==-20 && yPos==-20){
        	agent.msgAtStart();
        }*/
    	if((waiternumber==1 && xPos==140 && yPos==20)||(waiternumber==2 && xPos==110 && yPos==20)||(waiternumber==3 && xPos==80 && yPos==20)||(waiternumber==4 && xPos==50 && yPos==20)||(waiternumber==5 && xPos==20 && yPos==20)){
    		agent.msgAtStart();
    	}
    	if (xPos < xDestination)
            xPos++;
    		//xPos=xPos+3;
        else if (xPos > xDestination)
            xPos--;
        	//xPos=xPos-3;
        if (yPos < yDestination)
            yPos++;
        	//yPos=yPos+3;
        else if (yPos > yDestination)
            yPos--;
        	//yPos=yPos-3;

        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + 20) & (yDestination == yTable - 20)) {
        	//if (agent.ws == WaiterState.approachingTable)
        	agent.msgAtTable();
        }
        else if(xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + 150 + 20) & (yDestination == yTable - 20)) {
           agent.msgAtTable();
         }
        else if(xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + 150 + 20) & (yDestination == yTable - 20-150)) {
           agent.msgAtTable();
         }
        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == 200) & (yDestination == 20)) {
        	//if (agent.ws == WaiterState.approachingTable)
        	agent.msgAtCashier();
        }
        if (xPos == xDestination && yPos == yDestination
        		&& (((xDestination == xSeat)||(xDestination==xSeat-30)) && ((yDestination == ySeat)||(yDestination == ySeat+30)||(yDestination == ySeat+60)||(yDestination == ySeat+90)||(yDestination == ySeat+120)))) {
        	agent.msgAtWaitingCustomer();
        }
        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == 350) & (yDestination == 70)) {
        	//if (agent.ws == WaiterState.approachingTable)
        	agent.msgAtCook();
        }
    }

    public void draw(Graphics2D g) {
    	g.drawImage(waiterImage, xPos, yPos, null);
        /*g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, 20, 20);
        
        Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.drawString(agent.getName(), xPos, yPos-2);*/
    }

    public boolean isPresent() {
        return true;
    }

	public void setRequestedBreak() {
		requestedBreak = true;
		agent.msgWantABreak();
		//setPresent(true);
	}
	public boolean requestedBreak() {
		return requestedBreak;
	}
	
    public void DoBringToTable(TanCustomerRole customer, int seatnumber) {
        if (seatnumber==1){
        	xDestination = xTable + 20;
            yDestination = yTable - 20;
        }
        else if(seatnumber==2){
        	xDestination = xTable + 150 + 20;
            yDestination = yTable - 20;
        }
        else if(seatnumber==3){
        	xDestination = xTable + 150 + 20;
            yDestination = yTable - 20 - 150;
        }
    }
    
    public void GoToCashier(){
    	xDestination= 200;
    	yDestination= 20;
    }
    
    public void DoPickUpFood(){
    	xDestination =-20;
    	yDestination =-20;
    }
    
    public  void setWaiterEnabled(){
    	requestedBreak= false;
    	System.out.println("in setWaiterEnabled");
    	//gui.setWaiterEnabled(agent);
    }

    public void DoServeFood(int seatnumber){
    	if (seatnumber==1){
        	xDestination = xTable + 20;
            yDestination = yTable - 20;
        }
        else if(seatnumber==2){
        	xDestination = xTable + 150 + 20;
            yDestination = yTable - 20;
        }
        else if(seatnumber==3){
        	xDestination = xTable + 150 + 20;
            yDestination = yTable - 20 - 150;
        }
    }
    
    public void ApproachTable(int seatnumber){
    	if (seatnumber==1){
        	xDestination = xTable + 20;
            yDestination = yTable - 20;
        }
        else if(seatnumber==2){
        	xDestination = xTable + 150 + 20;
            yDestination = yTable - 20;
        }
        else if(seatnumber==3){
        	xDestination = xTable + 150 + 20;
            yDestination = yTable - 20 - 150;
        }
    }
    
    public void approachWaitingCustomer(int sn){
    	if(sn==1){
			xDestination = xSeat;
			yDestination = ySeat;
			//command=Command.GoToWaitingSeat;
		}
		if(sn==2){
			xDestination = xSeat-30;
			yDestination = ySeat;
			//command=Command.GoToWaitingSeat;
		}
		if(sn==3){
			xDestination = xSeat;
			yDestination = ySeat+30;
			//command=Command.GoToWaitingSeat;
		}
		if(sn==4){
			xDestination = xSeat-30;
			yDestination = ySeat+30;
			//command=Command.GoToWaitingSeat;
		}
		if(sn==5){
			xDestination = xSeat;
			yDestination = ySeat+60;
			//command=Command.GoToWaitingSeat;
		}
		if(sn==6){
			xDestination = xSeat-30;
			yDestination = ySeat+60;
			//command=Command.GoToWaitingSeat;
		}
		if(sn==7){
			xDestination = xSeat;
			yDestination = ySeat+90;
			//command=Command.GoToWaitingSeat;
		}
		if(sn==8){
			xDestination = xSeat-30;
			yDestination = ySeat+90;
			//command=Command.GoToWaitingSeat;
		}
		if(sn==9){
			xDestination = xSeat;
			yDestination = ySeat+120;
			//command=Command.GoToWaitingSeat;
		}
		if(sn==10){
			xDestination = xSeat-30;
			yDestination = ySeat+120;
			//command=Command.GoToWaitingSeat;
		}
    }
    
    public void DoLeaveCustomer() {
    	/*
        xDestination = -20;
        yDestination = -20;*/
    	if(waiternumber==1){
			xDestination = 140;
			yDestination = 20;
		}
		if(waiternumber==2){
			xDestination = 110;
			yDestination = 20;
		}
		if(waiternumber==3){
			xDestination = 80;
			yDestination = 20;
		}
		if(waiternumber==4){
			xDestination = 50;
			yDestination = 20;
		}
		if(waiternumber==5){
			xDestination = 20;
			yDestination = 20;
		}
    }
    
    public void DoGoToCook(){
    	xDestination = 350;
    	yDestination = 70;
    }
    
    public boolean isAtStart(){
    	/*
    	if ((xPos == -20) &&(yPos==-20)){
    		return true;
    	}*/
    	if((waiternumber==1 && xPos==140 && yPos==20)||(waiternumber==2 && xPos==110 && yPos==20)||(waiternumber==3 && xPos==80 && yPos==20)||(waiternumber==4 && xPos==50 && yPos==20)||(waiternumber==5 && xPos==20 && yPos==20)){
    		return true;
    	}
    	else return false;
    }

    /*
    public boolean isAtTable(){
    	if ((xPos == xTable +20) && (yPos== yTable-20)){
    		agent.msgisAtTable();
    		return true;
    	}
    	if ((xPos == xTable +150+ 20) && (yPos== yTable-20)){
    		agent.msgisAtTable();
    		return true;
    	}
    	if ((xPos == xTable +150 +20) && (yPos== yTable-20 -150)){
    		agent.msgisAtTable();
    		return true;
    	}
    	else return false;
    	//agent.msgisAtTable();//myc);
    }*/
    
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
