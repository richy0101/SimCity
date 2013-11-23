package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class CityComponent {
	Rectangle rectangle;
	int x, y;
	Color color;
	String ID;
	boolean isActive;
	
	public CityComponent() {
		x = 0;
		y = 0;
		color = Color.black;
		ID = "";
		isActive = true;
	}
	
	public CityComponent(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		color = c;
		ID = "";
		isActive = true;
	}
	
	public CityComponent(int x, int y, Color c, String I) {
		this.x = x;
		this.y = y;
		color = c;
		ID = I;
		isActive = true;
	}
	
	public abstract void updatePosition();
	
	public void paint(Graphics g) {
		if(isActive) {
			g.setColor(color);
			g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
	}
	
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}
	
	public void disable() {
		isActive = false;
	}
	
	public void enable() {
		isActive = true;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setX(int x) {
		this.x = x;
		rectangle.setLocation(x,y);
	}
	
	public void setY(int y) {
		this.y = y;
		rectangle.setLocation(x,y);
	}
	
	public void setPosition(Point p) {
		this.x = p.x;
		this.y = p.y;
		rectangle.setLocation(p);
	}

}
