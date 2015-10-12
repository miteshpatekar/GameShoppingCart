import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import javax.servlet.http.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class AddToCart extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	

	 static HashMap<Integer,Product> hmap ;
	public void init(){
		//Connect to Mongo DB
		MongoClient mongo = new MongoClient("localhost", 27017);
						
		// if database doesn't exists, MongoDB will create it for you
		DB db = mongo.getDB("CustomerReviews");
		
		DBCollection myReviews = db.getCollection("myReviews");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		response.setContentType("text/html");		
		PrintWriter out = response.getWriter();

		int productId= 	Integer.parseInt(request.getParameter("productId"));
		HttpSession s=request.getSession();
  		List<Cart> list= (List<Cart>) s.getAttribute("list");

  			if(list==null){
    			list =new ArrayList<>();
  			}

		HashMapProducts hmp=new HashMapProducts();
		  hmp.setHashMapProduct();
		  hmap=hmp.getHashMapProduct();
		  Iterator<Integer> productIterator=hmap.keySet().iterator();
		   while(productIterator.hasNext())
	        {
	            Integer id=productIterator.next();

	            Product p=hmap.get(id);
	            if(p.Id==productId)

	            {
	            	
  // Add the name & cost to List
  				list.add(new Cart(productId,p.Name,p.price,1));

  				s.setAttribute("list",list);
	           	out.println("Successfully Added to cart" +p.Name);
	            }
	            
	        }

		  out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Cart Items</h1>");
			
			out.println("<table>");
			for(Cart cart : list){
   				
   				out.println("<tr>");
				out.println("<td>");
				out.println(cart.Name);

				out.println("</td>");
				out.println("<td>");
				out.println(cart.price);
				
				out.println("</td>");
				out.println("<td>");
				out.println("<form class = 'submit-button' method = 'get' action = 'CheckOut'>");
				out.println("<input type='text' name = 'quantity' value = '"+cart.quantity+"'>");
				
				out.println("</td>");
				out.println("</tr>");
			
 //  out.println("Cost "+ cart.getCost());
 			}
 			//out.println("Total Value ="+cart.quantity+"'>");
 			out.println("<form class = 'submit-button' method = 'get' action = 'CheckOut'>");
			out.println("<input type='submit' name = 'submit' value = 'Check Out'>");
			out.println("</form>");
			
			out.println("</table>");

			
			out.println("</body>");
			out.println("</html>");
		 
	        
	        try{
								
	    } catch (MongoException e) {
		e.printStackTrace();
	    }

	}
}