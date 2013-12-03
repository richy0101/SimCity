package restaurant.interfaces;

import java.util.Vector;

import restaurant.gui.Bill;

public interface Market {

	void msgHereIsPayment(Bill b);

	void msgOrderForReplenishment(Vector<String> lowItems, Cook cookAgent,
			Cashier cashier);

}