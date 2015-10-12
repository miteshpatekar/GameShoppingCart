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

			HttpSession s=request.getSession();
    		Object username=	s.getAttribute("username");
			
  			PrintWriter out = response.getWriter();
  			
  			List<Cart> list= (List<Cart>) s.getAttribute("list");
  			Map<String, Object> products = new BasicDBObject();
			List<Integer> cartList = new ArrayList<>();
  			int total=0;
  			for(Cart cart : list){

   				total=total +(cart.price *cart.quantity);
				products.put("prodcut",cart);
			
				cartList.add(cart.Id);
 		out.println("id "+ cart.Id);
 			}


out.println("yayycdd");
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
    		
    		//commandArguments.put("product", product);
    		BasicDBObject doc = new BasicDBObject(commandArguments);
			myOrders.insert(doc);
			
			
			Object id = (Object)doc.get( "_id" );

			out.println("order placed successfully" + id);
		//	request.getRequestDispatcher("/").forward(request, response);

	
			
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	
}