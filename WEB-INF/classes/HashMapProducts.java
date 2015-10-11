import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapProducts {
	static HashMap<Integer,Product> hmap = new HashMap<Integer, Product>();

    /*Adding elements to HashMap*/
   
	
	//images/img_XBoxOriginal.jpg
	public void setHashMapProduct() {
		Product p1=new Product(1,"XBox One","Microsft","images/XBoxOne.jpg",20);
		Product p2=new Product(2,"XBox 360","Microsft","images/XBox360.jpg",5);
		Product p3=new Product(3,"PS3","Sony","images/PS3_1.jpg",100);
		Product p4=new Product(4,"PS4","Sony","images/PS3_4.jpg",10);
		
		// TODO Auto-generated method stub
		 hmap.put(p1.Id, p1);
		 hmap.put(p2.Id,p2);
		 hmap.put(p3.Id, p3);
		 hmap.put(p4.Id, p4);	  

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
