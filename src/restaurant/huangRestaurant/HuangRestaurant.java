package restaurant.huangRestaurant;




import restaurant.Restaurant;



public class HuangRestaurant extends Restaurant {

	private String name;
	HuangHostAgent host;
	HuangCashierAgent cashier;
	double till = 10000;
	
	

	public HuangRestaurant(String name) {
		super();
		this.name = name;
		cashier = new HuangCashierAgent("Money Machine 9001");
		host = new HuangHostAgent("Host", cashier);
		host.startThread();
		cashier.startThread();	
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public HuangHostAgent getHost() {
		return host;
	}
	
	public HuangCashierAgent getCashier() {
		return cashier;
	}
	
	public double getTill() {
		return till;
	}

	public void setTill(double till) {
		this.till = till;
	}

}
