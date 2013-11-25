package bank.gui;

import gui.Gui;

import java.awt.Graphics2D;

import bank.*;


public class BankManagerGui implements Gui {
	
    
	private BankManagerRole agent = null;
    
	public int xPos = 50, yPos = 200;//default manager position
    
	public BankManagerGui(BankManagerRole agent) {
        this.agent = agent;
	}
	
	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
        
	}
    
	@Override
	public void draw(Graphics2D g) {
		// assign custom pokemon graphics to manager
        
	}
    
}
