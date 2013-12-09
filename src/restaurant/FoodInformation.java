package restaurant;

public class FoodInformation {
	protected int quantity;
	protected String name;
	protected int cookTime;
	
	public FoodInformation() {
	}
	
	public FoodInformation(int cookingTime, int inventory) {
		quantity = inventory;
		cookTime = cookingTime;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setCookingTime(int cookTime) {
		this.cookTime = cookTime;
	}
	
	public int getCookTime() {
		return cookTime;
	}
}
