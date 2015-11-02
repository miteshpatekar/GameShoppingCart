
public class Product {
	int Id;
	int MGId;
	String Name;
	String Manufacturer;
	String Category;
	String RetailerName;
	String imagePath;
	int price;
	int quantity;

	
	public Product(int id, String Name,String manf,String category,String rname,String imagePath,int price){
		Id=id;
		this.Name=Name;
		this.price=price;
		this.Manufacturer=manf;
		this.Category=category;
		this.RetailerName=rname;
		this.imagePath=imagePath;

	}

}
