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
import java.util.*;
import javax.servlet.*;
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

public class OrderPlace extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{


			String address = request.getParameter("address");
			int cardno = Integer.parseInt(request.getParameter("cardno"));
			String validity = request.getParameter("validity");
			int ccv = Integer.parseInt(request.getParameter("ccv"));

			HttpSession s=request.getSession();
    		String username=(String)s.getAttribute("userName");
			
  			PrintWriter out = response.getWriter();
  			
  			List<Cart> list= (List<Cart>) s.getAttribute("list");
  			Map<String, Object> products = new BasicDBObject();
			List<Integer> cartList = new ArrayList<>();
  			int total=0;
  			for(Cart cart : list){

   				total=total +(cart.price *cart.quantity);
				products.put("prodcut",cart);
			
				cartList.add(cart.Id);
 		
 			}

			DB db = mongo.getDB("gameWebsite");
			// If the collection does not exists, MongoDB will create it for you
			Map<String, Object> commandArguments = new BasicDBObject();
			DBCollection myOrders = db.getCollection("orders");
			commandArguments.put("username", username);
    		commandArguments.put("total", total);
    		//String[] roles = { "readWrite" };
    		commandArguments.put("items", cartList);
    		commandArguments.put("status", "inprocess");
    		commandArguments.put("isCancelled", "no");
    		commandArguments.put("address", address);
    		commandArguments.put("cardno", cardno);
    		commandArguments.put("validity", validity);
    		commandArguments.put("ccv", ccv);
    		
    		//commandArguments.put("product", product);
    		BasicDBObject doc = new BasicDBObject(commandArguments);
			myOrders.insert(doc);
			
			
			Object id = (Object)doc.get( "_id" );
response.setContentType("text/html");

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
    		out.println("<li class='end'><a href='/GameWebsite/MyOrders'>My Orders</a></li>"); 
    		out.println("<li class='end' style='float:right'><a href='/GameWebsite/LogOut'>Log Out</a></li>"); 
    	}
    	

        out.println("</ul>");
    out.println("</nav>");

	out.println("<img class='header-image' src='images/indexheader.jpg' width = '100%' height = '100%' alt='Index Page Image' />");

    out.println("<div id='body'>");		

	out.println("<section id='content'>");

	    out.println("<article>");
			out.println("<h2>Microsoft Products");out.println("</h2>");
			
			out.println("</article>");
	out.println("<article class='expanded'>");

out.println("<table>");
	out.println("<tr>");
				out.println("<td>");
				out.println("Congratulations !!");

				out.println("</td>");
				out.println("</tr>");
out.println("<tr>");
				out.println("<td>");
				out.println("Order Placed successfully");

				out.println("</td>");
				out.println("</tr>");
out.println("<tr>");
				out.println("<td>");
				out.println("Your Order Id is " + id);

				out.println("</td>");
				out.println("</tr>");
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
			
			
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	
}