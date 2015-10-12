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
		String username=(String)s.getAttribute("userName");
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

out.println("<head>");
	out.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
	out.println("<title>GameZon");
	out.println("</title>");
	out.println("<link rel='stylesheet' href='styles.css' type='text/css' />");
out.println("</head>");

out.println("<body>");
out.println("<div id='container'>");
    out.println("<header>");
    	out.println("<h1>");out.println("<a href='/'>Game");out.println("<span>Zon");out.println("</span>");
    	out.println("</a>");out.println("</h1>");  
    	if(username!=null)
    	{
    		out.println("<h5 style='float:right'>Welcome "+ username+" !</h5>"); 
    	}
    	
    out.println("</header>");
    out.println("<nav>");
    	out.println("<ul>");
        	out.println("<li class='start selected'>");out.println("<a href='index.html'>Home");out.println("</a>");out.println("</li>");
            out.println("<li class=''>");out.println("<a href='/GameWebsite/Microsoft'>Microsoft");
            out.println("</a>");out.println("</li>");
            out.println("<li class=''>");out.println("<a href='/GameWebsite/Sony'>Sony");
            out.println("</a>");out.println("</li>");
            out.println("<li class='end'>");out.println("<a href='/GameWebsite/Nintendo'>Nintendo");
            out.println("</a>");out.println("</li>");
             out.println("<li class='end'>");out.println("<a href='/GameWebsite/Accessories'>Accessories");
             out.println("</a>");out.println("</li>");
            if(username==null){
           out.println("<li class='end'>");out.println("<a href='signin.html'>Sign In");
            out.println("</a>");out.println("</li>");
            out.println("<li class='end'>");out.println("<a href='signup.html'>Sign Up");
            out.println("</a>");out.println("</li>");
            }
            
            if(username!=null)
    	{
    		out.println("<li class='end' style='float:right'><a href='/GameWebsite/LogOut'>Log Out</a></li>"); 
    	}
    	

        out.println("</ul>");
    out.println("</nav>");

	out.println("<img class='header-image' src='images/indexheader.jpg' width = '100%' height = '100%' alt='Index Page Image' />");

    out.println("<div id='body'>");		

	out.println("<section id='content'>");

	    out.println("<article>");
			out.println("<h2>Your Cart");out.println("</h2>");
			
			out.println("</article>");
	out.println("<article class='expanded'>");

out.println("<table>");
out.println("<tr>");
				out.println("<td>");
				out.println("Name");

				out.println("</td>");
				out.println("<td>");
				out.println("Price");
				
				out.println("</td>");
				out.println("<td>");
				out.println("Quantity");
				out.println("</td>");
				out.println("</tr>");
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
 			out.println("<tr>");
				out.println("<td>");

	out.println("<form class = 'submit-button' method = 'get' action = 'CheckOut'>");
			out.println("<input type='submit' name = 'submit' value = 'Check Out'>");
			out.println("</form>");
				out.println("</td>");
				out.println("<td>");
				
				out.println("</tr>");
 			//out.println("Total Value ="+cart.quantity+"'>");
 		
			
			out.println("</table>");





out.println("</article>");
		
    out.println("</section>");
        
    out.println("<aside class='sidebar'>");
	
            out.println("<ul>");	
               out.println("<li>");
                    out.println("<h4>Our Products");out.println("</h4>");
                    out.println("<ul>");
                        out.println("<li>");out.println("<a href='/GameWebsite/Microsoft'>Microsoft");out.println("</a>");out.println("</li>");
                        out.println("<li>");out.println("<a href='/GameWebsite/Sony'>Sony");
                        out.println("</a>");out.println("</li>");
                        out.println("<li>");out.println("<a href='/GameWebsite/Nintendo'>Nintendo");
                        out.println("</a>");out.println("</li>");
                        out.println("<li>");out.println("<a href='/GameWebsite/Accessories'>Accessories");
                        out.println("</a>");out.println("</li>");
                    out.println("</ul>");
                out.println("</li>");                                       
            out.println("</ul>");
		
    out.println("</aside>");
    
	out.println("<div class='clear'>");out.println("</div>");
	out.println("</div>");
    
	out.println("<footer>");
	
        out.println("<div class='footer-bottom'>");
            out.println("<p>Let the Game began !");out.println("</p>");
        out.println("</div>");
		
    out.println("</footer>");
out.println("</div>");

out.println("</body>");

out.println("</html>");
			




	        
	        try{
								
	    } catch (MongoException e) {
		e.printStackTrace();
	    }

	}
}