
public class Cart {
	int Id;	
	String Name;	
	int price;
	int quantity;
	
	public Cart(int id, String Name,int price,int quantity){
		Id=id;
		this.Name=Name;
		this.price=price;
		this.quantity=quantity;
	}

	public String getName(){
		return this.Name;
	}
	public void setQuantity(int q){
		this.quantity=q;
	}

}
