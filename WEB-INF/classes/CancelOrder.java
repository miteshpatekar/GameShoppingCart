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
import java.lang.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import org.bson.types.ObjectId;

public class CancelOrder extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;

	 static HashMap<Integer,Product> hmap ;
	public void init(){
		//Connect to Mongo DB
		 mongo = new MongoClient("localhost", 27017);

	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		response.setContentType("text/html");		
		PrintWriter out = response.getWriter();

		String orderId= request.getParameter("orderId");

		out.println(orderId);
		HttpSession s=request.getSession();
		String username=(String)s.getAttribute("userName");
  		List<Cart> list= (List<Cart>) s.getAttribute("list");

  			if(list==null){
    			list =new ArrayList<>();
  			}

  			DB db = mongo.getDB("gameWebsite");
				ObjectId objid =new ObjectId(orderId);
			DBCollection orders = db.getCollection("orders");
  	BasicDBObject newDocument = new BasicDBObject();
	newDocument.append("$set", new BasicDBObject().append("isCancelled", "yes"));
			newDocument.append("$set", new BasicDBObject().append("status", "Cancelled"));
	BasicDBObject searchQuery = new BasicDBObject().append("_id", objid);

	orders.update(searchQuery, newDocument);





		// HashMapProducts hmp=new HashMapProducts();
		//   hmp.setHashMapProduct();
		//   hmap=hmp.getHashMapProduct();
		//   Iterator<Integer> productIterator=hmap.keySet().iterator();
		//    while(productIterator.hasNext())
	 //        {
	 //            Integer id=productIterator.next();

	 //            Product p=hmap.get(id);
	           /* if(p.Id==orderId)

	            {
	            	
  // Add the name & cost to List
  				list.add(new Cart(productId,p.Name,p.price,1));

  				s.setAttribute("list",list);
	           	out.println("Successfully Added to cart" +p.Name);
	            }*/
	            
	       // }

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

		out.println("Cancelled order successfully ");



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