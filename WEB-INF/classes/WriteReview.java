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
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.*;

public class WriteReview extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	String productName = "";
	String imageLocation = " ";
	String manufacturer = " ";
	int productPrice = 0;
	String category;
	String retailer;
	 static HashMap<Integer,Product> hmap ;
	public void init(){
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		int productId= 	Integer.parseInt(request.getParameter("productId"));
		HttpSession s=request.getSession();
		String username=(String)s.getAttribute("userName");
		if(username==null)
		{
			response.sendRedirect("signin.html");
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
	            	imageLocation=p.imagePath;
 					productName=p.Name;
 					productPrice=p.price;  	
 					manufacturer=p.Manufacturer;		
 					category=p.Category;
 					retailer=p.RetailerName;	
	            }
	            
	        }


						
		try{
			//Get the values from the form
			
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
			out.println("<h2>Welcome to our Game World");out.println("</h2>");
			
            out.println(" <h3>" +productName+ "</h3> ");
			out.println("<form method=\"get\" action=\"SubmitReview\">");
			out.println("<fieldset>");
			out.println("<legend>Product information:</legend>");
			out.println("<img src =\" "+imageLocation+" \"width = \"200\" height = \"200\" alt = \"X Box Orginal\">");
			out.println("<table>");
			out.println("<tr>");
			out.println("<td> Product Name: </td>");
			out.println("<td>"+productName+"</td>");
			out.println("</tr>");						
			out.println("<tr>");
			out.println("<td> Product Price: </td>");
			out.println("<td> "+productPrice+" </td>");
			out.println("</tr>");


			out.println("<tr>");
			out.println("<td> Manufacturer: </td>");
			out.println("<td> "+manufacturer+" </td>");
			out.println("</tr>");

			out.println("</table>");
			out.println("</fieldset>");
			out.println("<fieldset>");
			out.println("<legend>Reviews:</legend>");
			out.println("<table>");
			
			out.println("<tr>");
			out.println("<td> Product Category: </td>");
			out.println("<td> <input type=\"text\" name=\"category\" value=\""+category+"\"> </td>");
			out.println("</tr>");
			out.println("<tr>");

			out.println("<tr>");
			out.println("<td> Product Name: </td>");
			out.println("<td> <input type=\"text\" name= \"productName\" value = \""+productName+"\">  </td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td> User Name: </td>");
			out.println("<td> <input type=\"text\" name=\"userName\" value=\""+username+"\"> </td>");
			out.println("</tr>");
			out.println("<tr>");

			out.println("<tr>");
			out.println("<td> Manufacturer: </td>");
			out.println("<td> <input type=\"text\" name=\"manufacturer\" value=\""+manufacturer+"\"> </td>");
			out.println("</tr>");
			out.println("<tr>");

			out.println("<tr>");
			out.println("<td> Product Retailer: </td>");
			out.println("<td> <input type=\"text\" name=\"retailer\" value=\""+retailer+"\"> </td>");
			out.println("</tr>");
			out.println("<tr>");

			out.println("<tr>");
			out.println("<td> Price: </td>");
			out.println("<td> <input type=\"number\" name=\"productPrice\" value=\""+productPrice+"\"> </td>");
			out.println("</tr>");
			out.println("<tr>");


			out.println("<tr>");
			out.println("<td> User Age: </td>");
			out.println("<td> <input type=\"number\" name=\"age\" value=''> </td>");
			out.println("</tr>");
			out.println("<tr>");

			out.println("<tr>");
			out.println("<td> User Gender: </td>");
			out.println("<td> <input type=\"text\" name=\"gender\" value=''> </td>");
			out.println("</tr>");
			out.println("<tr>");

			out.println("<tr>");
			out.println("<td> User Ocupation: </td>");
			out.println("<td> <input type=\"text\" name=\"ocupation\" value=''> </td>");
			out.println("</tr>");
			out.println("<tr>");

			out.println("<tr>");
			out.println("<td> Retailer City: </td>");
			out.println("<td> <input type=\"text\" name=\"rcity\" value=''> </td>");
			out.println("</tr>");
			out.println("<tr>");

			out.println("<tr>");
			out.println("<td> Retailer State: </td>");
			out.println("<td> <input type=\"text\" name=\"rstate\" value=''> </td>");
			out.println("</tr>");
			out.println("<tr>");

			out.println("<tr>");
			out.println("<td> Retailer Zip: </td>");
			out.println("<td> <input type=\"number\" name=\"rzip\" value=''> </td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td> Product on Sale: </td>");
			out.println("<td>");
			out.println("<select name=\"psale\">");
			out.println("<option value=\"yes\" selected>Yes</option>");
			out.println("<option value=\"no\">No</option>");
			out.println("</td>");
			out.println("</tr>");

			out.println("<tr>");
			out.println("<td> Manufacture Rebate: </td>");
			out.println("<td>");
			out.println("<select name=\"rebate\">");
			out.println("<option value=\"yes\" selected>Yes</option>");
			out.println("<option value=\"no\">No</option>");
			out.println("</td>");
			out.println("</tr>");

			out.println("<tr>");
			out.println("<td> Review Rating: </td>");
			out.println("<td>");
			out.println("<select name=\"reviewRating\">");
			out.println("<option value=\"1\" selected>1</option>");
			out.println("<option value=\"2\">2</option>");
			out.println("<option value=\"3\">3</option>");
			out.println("<option value=\"4\">4</option>");
			out.println("<option value=\"5\">5</option>");
			out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td> Review Date: </td>");
			out.println("<td> <input type=\"text\" name=\"reviewDate\"> </td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td> Review Text: </td>");
			out.println("<td><textarea name=\"reviewText\" rows=\"4\" cols=\"50\"> </textarea></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<br><br>");
			out.println("<input type=\"submit\" value=\"Submit Review\">");
			out.println("</fieldset>");
			out.println("</form>");	
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