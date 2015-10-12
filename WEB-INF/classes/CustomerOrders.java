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
import com.mongodb.BasicDBList;
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
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class CustomerOrders extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
     static HashMap<Integer,Product> hmap ;
	public void init(){
        mongo = new MongoClient("localhost", 27017);
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		HashMapProducts hmp=new HashMapProducts();
          hmp.setHashMapProduct();
          hmap=hmp.getHashMapProduct();
         
		PrintWriter out = response.getWriter();
			HttpSession s=request.getSession();
    		String username=(String)s.getAttribute("userName");
            String role=(String)s.getAttribute("role");
            DB db = mongo.getDB("gameWebsite");
            DBCollection collection = db.getCollection("orders");
            BasicDBObject whereQuery = new BasicDBObject();
            //whereQuery.put("", );
            //BasicDBObject whereQuery1 = new BasicDBObject();
            //whereQuery.put("password", pwd);
            DBCursor cursor = collection.find();
           
                
            
                System.out.println("invalid username pwd");

		  					
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
        	out.println("<li>");out.println("<a href='index.html'>Home");out.println("</a>");out.println("</li>");
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
                out.println("<li class='start selected'><a href='/GameWebsite/CustomerOrders'>Customer Orders</a></li>"); 
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
			out.println("<h2>All Orders");out.println("</h2>");
			out.println("<table>");
            List<Integer> cartList = new ArrayList<>();

             out.println("<tr>");
                // out.println("<td>");
                //     out.println("Id");
                // out.println("</td>");
                out.println("<td>");
                    out.println("Name");
                out.println("</td>");
                 out.println("<td>");
                    out.println("Date");
                out.println("</td>");
                 out.println("<td>");
                    out.println("Status");
                out.println("</td>");
                 out.println("<td>");
                    out.println("Action");
                out.println("</td>");
                out.println("<td>");
                    out.println("Action");
                out.println("</td>");
                out.println("<td>");
                    out.println("Action");
                out.println("</td>");
                 out.println("</tr>");
                
                /* if(cursor.length()==0)
                 {
                    out.println("<tr><td><h5>No Orders</h5></td></tr>");
                 }*/

             while(cursor.hasNext()) {

                BasicDBObject obj = (BasicDBObject) cursor.next();

            
               // out.println("<td>");
                  //  out.println(obj.get("_id"));
                    Object orderId=(Object)obj.get("_id");

              //  out.println("</td>");
               
 BasicDBList itemsList = (BasicDBList) obj.get("items");
    for (int i = 0; i < itemsList.size(); i++) {
        out.println("<tr>");
        out.println("<td>");
                  //  out.println(itemsList.get(i));
 Iterator<Integer> productIterator=hmap.keySet().iterator();
           while(productIterator.hasNext())
            {
                Integer id=productIterator.next();

                Product p=hmap.get(id);
                if(p.Id==(int)itemsList.get(i))
                {
                     out.println(p.Name);
                               
                }
                
            }

                out.println("</td>");
             
            out.println("<td>");
                    out.println(obj.get("date"));
                out.println("</td>");
            out.println("<td>");
                    out.println(obj.get("status"));
                out.println("</td>");
                
               
                
                    out.println("<td>");
                   out.println("<form class = 'submit-button' method = 'get' action = 'AddOrder'>");
                   out.println("<input type='hidden' name = 'orderId' value = '"+orderId.toString()+"'>");
                out.println("<input type='submit' name = 'quantity' value = 'Add'>");
                out.println("</form>");
                out.println("</td>");
                
                 out.println("<td>");
                   out.println("<form class = 'submit-button' method = 'get' action = 'UpdateOrder'>");
                   out.println("<input type='hidden' name = 'orderId' value = '"+orderId.toString()+"'>");
                out.println("<input type='submit' name = 'quantity' value = 'Update'>");
                out.println("</form>");
                out.println("</td>");

                out.println("<td>");
                   out.println("<form class = 'submit-button' method = 'get' action = 'DeleteOrder'>");
                   out.println("<input type='hidden' name = 'orderId' value = '"+orderId.toString()+"'>");
                out.println("<input type='submit' name = 'quantity' value = 'Delete'>");
                out.println("</form>");
                out.println("</td>");

            out.println("</tr>");
  }

// cartList=(List<Integer>)obj.get("items");
// for(int c:cartList)
// {
//      out.println(c);
// }



                //out.println(obj.get("username"));
                //HttpSession s=request.getSession();
               // s.setAttribute("userName",uname);
                //System.out.println("user logged in successfully");
                //request.getRequestDispatcher("/Index").forward(request, response);
                }

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
}