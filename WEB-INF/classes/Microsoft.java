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

public class Microsoft extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	String productName = "";
	String imageLocation = " ";
	int productPrice = 0;
	 static HashMap<Integer,Product> hmap ;
	public void init(){
		//Connect to Mongo DB
		MongoClient mongo = new MongoClient("localhost", 27017);
						
		// if database doesn't exists, MongoDB will create it for you
		DB db = mongo.getDB("CustomerReviews");
		
		DBCollection myReviews = db.getCollection("myReviews");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();

		HashMapProducts hmp=new HashMapProducts();
		  hmp.setHashMapProduct();
		  hmap=hmp.getHashMapProduct();
		  Iterator<Integer> productIterator=hmap.keySet().iterator();
		  
						
		try{
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Microsft</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Microsoft</h1>");							
			out.println("<table>");
while(productIterator.hasNext())
	        {
	            Integer id=productIterator.next();
	            Product p=hmap.get(id);
	            System.out.println(p.Name+"----");
	       
			out.println(p.Name);
				
				out.println("<table>");
			out.println("<tr>");
				out.println("<td>");
					out.println("<img src = '"+p.imagePath+"' width = '200' height = '200' alt = 'X Box Orginal'>");
				out.println("</td>");
				out.println("<td>");
					out.println("<p>"); 
					 out.println("</p>");
				out.println("<td>");
				out.println("<td>");
					out.println("<form class = 'submit-button' method = 'get' action = 'AddToCart'>");
						out.println("<input type='hidden' name = 'productId' value = '"+p.Id+"'>");
						out.println("<input class = 'submit-button' type = 'submit'  value = 'Add To Cart'>");
					out.println("</form>");
					out.println("<form class = 'submit-button' method = 'get' action = 'WriteReview'>");
						out.println("<input class = 'submit-button' type = 'submit' name = 'XBox_Original' value = 'Write Review'>");
					out.println("</form>");
					out.println("<form class = 'submit-button' method = 'get' action = 'ViewReviews'>");
						out.println("<input class = 'submit-button' type = 'submit' name = 'XBox_Original' value = 'View Reviews'>");
					out.println("</form>");
				out.println("</td>");
			out.println("</tr>");

			
			  }
			out.println("</body>");
						
	    } catch (MongoException e) {
		e.printStackTrace();
	    }

	}
}