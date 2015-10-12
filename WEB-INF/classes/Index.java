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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.*;

public class Index extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	public void init(){
			// mongo = new MongoClient("localhost", 27017);
			// DB db = mongo.getDB("gameWebsite");
				
			// // for adding storeManager user signup
			// Map<String, Object> commandArguments = new BasicDBObject();
			// DBCollection users = db.getCollection("users");


			// BasicDBObject whereQuery = new BasicDBObject();
			// whereQuery.put("username", "storeadmin");
			// DBCursor cursor = users.find(whereQuery);
			// int flag1=0;
			// while(cursor.hasNext()) {
			// 	flag1=1;	    		
			// 	}

			// 	if(flag1==0)
			// 	{
			// 		commandArguments.put("email", "storemanager@gamezon.com");
			// 		commandArguments.put("username", "storeadmin");
   //  				commandArguments.put("password", "sa123");
   //  				commandArguments.put("role", "storeManager");  			
   //  				BasicDBObject doc = new BasicDBObject(commandArguments);
			// 		users.insert(doc);
			// 	}
			


			// Map<String, Object> commandArguments1 = new BasicDBObject();
			// BasicDBObject whereQuery1 = new BasicDBObject();
			// whereQuery1.put("username", "salesadmin");
			// DBCursor cursor1 = users.find(whereQuery1);
			// int flag2=0;
			// while(cursor1.hasNext()) {
			// 	flag2=1;	    		
			// 	}

			// 	if(flag2==0)
			// 	{
			// 		commandArguments1.put("email", "salesman@gamezon.com");
			// 		commandArguments1.put("username", "salesadmin");
   //  				commandArguments1.put("password", "sa123");
   //  				commandArguments1.put("role", "salesMan");    	
   //  				BasicDBObject doc1 = new BasicDBObject(commandArguments1);
			// 		users.insert(doc1);
			// 	}
			
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
			HttpSession s=request.getSession();
    		String username=(String)s.getAttribute("userName");
		  	String role=(String)s.getAttribute("role");					
		try{


			
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
            if(role!=null)
            {
         
            if(role.equals("customer"))
            {
            	out.println("<li class='end'><a href='/GameWebsite/MyOrders'>My Orders</a></li>"); 
            }
            if(role.equals("salesMan"))
            {
            	out.println("<li class='end'><a href='/GameWebsite/CustomerOrders'>Customer Orders</a></li>"); 
            }
            if(role.equals("storeManager"))
            {
            	out.println("<li class='end'><a href='/GameWebsite/UpdateProducts'>Update Products</a></li>"); 
            }
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
			out.println("<h2>Welcome to our Game World");out.println("</h2>");
			
            out.println("<p>Check out our new products to have fun with games");out.println("</p>");	
            
            out.println("<p>Get the best Products delivered at your door");out.println("</p>");		
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
}