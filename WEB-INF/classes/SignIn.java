import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
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

public class SignIn extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
PrintWriter out = response.getWriter();
			//Get the values from the form
			String uname = request.getParameter("uname");
			String pwd = request.getParameter("pwd");

			DB db = mongo.getDB("gameWebsite");
			DBCollection collection = db.getCollection("users");
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("username", uname);
			//BasicDBObject whereQuery1 = new BasicDBObject();
			whereQuery.put("password", pwd);
			DBCursor cursor = collection.find(whereQuery);
			while(cursor.hasNext()) {

	    		
	    		//out.println(cursor.next());
	    		//out.println(cursor.next().get("username"));
	    		//out.println(cursor.next().find( { type: 'username' } ));
	    		BasicDBObject obj = (BasicDBObject) cursor.next();
    			//out.println(obj.get("username"));
    			HttpSession s=request.getSession();
    			s.setAttribute("userId",uname);
			 	//System.out.println("user logged in successfully");
				request.getRequestDispatcher("/").forward(request, response);
				}
				
			
				System.out.println("invalid username pwd");
			
			 

			//MongoClient mongo = new MongoClient("localhost", 27017);
   

			// String firstName = request.getParameter("firstName");
			// String lastName = request.getParameter("lastName");
			// String address = request.getParameter("address");
			// int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));							
										
			// // If database doesn't exists, MongoDB will create it for you
			// DB db = mongo.getDB("CSP595Tutorial");
				
			// // If the collection does not exists, MongoDB will create it for you
			// DBCollection myOrders = db.getCollection("myOrders");
			// System.out.println("Collection myOrders selected successfully");
				
			// BasicDBObject doc = new BasicDBObject("title", "myOrders").
			// 	append("productName", productName).
			// 	append("productPrice", productPrice).
			// 	append("firstName", firstName).
			// 	append("lastName", lastName).
			// 	append("address", address).
			// 	append("phoneNumber", phoneNumber);
					
			// myOrders.insert(doc);
				
			
			
			// //Send the response back to the JSP
			// PrintWriter out = response.getWriter();
			
			// out.println("<html>");
			// out.println("<head> </head>");
			// out.println("<body>");
			// out.println("<h1> Order placed for:"+ productName + "</h1>");
			
			// out.println("<table>");
			
			// out.println("<tr>");
			// out.println("<td>");
			// out.println("<a href='index.html'> Index </a>");
			// out.println("</td>");
			// out.println("</tr>");
			
			// out.println("<tr>");
			// out.println("<td>");
			// out.println("<a href='XBox.html'> X Box </a>");
			// out.println("</td>");
			// out.println("</tr>");
			
			// out.println("<tr>");
			// out.println("<td>");
			// out.println("<a href='PlayStation.html'> Play Station </a>");
			// out.println("</td>");
			// out.println("</tr>");
			
			// out.println("</table>");
			
			// out.println("</body>");
			// out.println("</html>");
			
			
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	
}