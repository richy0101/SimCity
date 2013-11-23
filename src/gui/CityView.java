package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JPanel;

public class CityView extends JPanel implements MouseListener, ActionListener {

	HashMap<String, CityCard> cards;
	SimCityGui city;
	public static final int VIEW_WIDTH = 500, VIEW_HEIGHT = 500;
	CardLayout layout;
	
	public CityView(SimCityGui city) {
		
		this.setPreferredSize(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
		this.setVisible(true);
		addMouseListener(this);
		this.city = city;
		
		cards = new HashMap<String, CityCard>();
		cards.put("null", new CityCard(city, Color.DARK_GRAY));
		cards.put("Road", new CityCard(city));
		cards.put("Restaurant 1", new CityCard(city, Color.blue));
		cards.put("Restaurant 2", new CityCard(city, Color.red));

		layout = new CardLayout();
		this.setLayout(layout);
		for (String key:cards.keySet()) {
			this.add(cards.get(key), key);
		}
		

		layout.show(this, "null");
	}
	
	public boolean addView(CityCard panel, String key) {
		if (cards.containsKey(key))
			return false;
		cards.put(key, panel);
		this.add(cards.get(key), key);
		return true;
	}
	
	public void setView(String key) {
		if (cards.containsKey(key)) {
			layout.show(this, key);
			//city.info.setText(key); //CHANGES INFO PANEL TO APPROPRIATE STRING OF BUILDING
		}
	}

	
	public void actionPerformed(ActionEvent arg0) {
		
	}

	
	public void mouseClicked(MouseEvent e) {
		
	}

	
	public void mouseEntered(MouseEvent e) {
		
	}

	
	public void mouseExited(MouseEvent e) {
		
	}

	
	public void mousePressed(MouseEvent e) {
		
	}

	
	public void mouseReleased(MouseEvent e) {
		
	}

}