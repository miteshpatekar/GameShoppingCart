import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapProducts {
	static HashMap<Integer,Product> hmap = new HashMap<Integer, Product>();

    /*Adding elements to HashMap*/
   
	
	//images/img_XBoxOriginal.jpg
	public void setHashMapProduct() {
		Product p1=new Product(1,"XBox One","Microsoft","images/XBoxOne.jpg",20);
		Product p2=new Product(2,"XBox 360","Microsoft","images/XBox360.jpg",5);
		Product p3=new Product(3,"PS3","Sony","images/PS3.jpg",100);
		Product p4=new Product(4,"PS4","Sony","images/PS4.jpg",10);
		Product p5=new Product(5,"Wii","Nintendo","images/Wii.jpg",100);
		Product p6=new Product(6,"WiiU","Nintendo","images/WiiU.jpg",10);
		Product p7=new Product(7,"JoyStick","Accessories","images/joystick.jpg",10);
		Product p8=new Product(8,"Steering","Accessories","images/steering.jpg",10);
		
		
		
		// TODO Auto-generated method stub
		 hmap.put(p1.Id, p1);
		 hmap.put(p2.Id,p2);
		 hmap.put(p3.Id, p3);
		 hmap.put(p4.Id, p4);	 
		 hmap.put(p5.Id, p5);
		 hmap.put(p6.Id,p6);
		 hmap.put(p7.Id, p7);
		 hmap.put(p8.Id, p8);	   

	}
	
	public HashMap<Integer,Product> getHashMapProduct() {
		
			return hmap;
	}

	
	public void iterateHashMap() {
		Iterator<Integer> productIterator=hmap.keySet().iterator();//put debug point at this line
		 
        while(productIterator.hasNext())
        {
            Integer id=productIterator.next();
            Product p=hmap.get(id);
            System.out.println(p.Name+"----");
            System.out.println(p.imagePath+"----");
        }

	}


}
