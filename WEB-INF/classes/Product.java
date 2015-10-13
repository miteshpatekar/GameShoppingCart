
public class Product {
	int Id;
	int MGId;
	String Name;
	String Manufacturer;
	String imagePath;
	int price;
	int quantity;

	
	public Product(int id, String Name,String manf,String imagePath,int price){
		Id=id;
		this.Name=Name;
		this.price=price;
		this.Manufacturer=manf;
		this.imagePath=imagePath;

	}

}
