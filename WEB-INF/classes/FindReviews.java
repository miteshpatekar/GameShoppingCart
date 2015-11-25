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
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.AggregationOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class FindReviews extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	String comparePrice ;
	String trending;
	String median;
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		PrintWriter output = response.getWriter();
					
		DB db = mongo.getDB("gameWebsite");
		
		// If the collection does not exists, MongoDB will create it for you
		DBCollection myReviews = db.getCollection("Reviews");
		
		BasicDBObject query = new BasicDBObject();
				
		try {
			
			// Get the form data
			String productName = request.getParameter("productName");
			String category = request.getParameter("category");
			int productPrice = Integer.parseInt(request.getParameter("productPrice"));
			int age = Integer.parseInt(request.getParameter("age"));
			String gender = request.getParameter("gender");
			String occupation = request.getParameter("occupation");
			String retailer = request.getParameter("retailer");
			String retailerZipcode = request.getParameter("retailerZipcode");
			String retailerCity = request.getParameter("retailerCity");
			String retailerState = request.getParameter("retailerState");
			
			String rebate = request.getParameter("rebate");
			String psale = request.getParameter("psale");

			int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
			String reviewDate = request.getParameter("reviewDate");
			String reviewText = request.getParameter("reviewText");

			String compareRating = request.getParameter("compareRating");
			comparePrice = request.getParameter("comparePrice");
			String compareAge = request.getParameter("compareAge");
			String compareLiked = request.getParameter("compareLiked");

			String returnValueDropdown = request.getParameter("returnValue");
			String groupByDropdown = request.getParameter("groupByDropdown");
			String sortByDropdown = request.getParameter("sortByDropdown");

			trending = request.getParameter("trending");
			median = request.getParameter("median");

			System.out.println("Trending"+trending);
			
			//Boolean flags to check the filter settings
			boolean noFilter = false;
			boolean filterByProduct = false;
			boolean filterByPrice = false;
			boolean filterByZip = false;
			boolean filterByCity = false;
			boolean filterByRating = false;
			
			boolean groupBy = false;
			boolean groupByCity = false;
			boolean groupByProduct = false;
			boolean groupByRetailerName=false;
			boolean groupByZip=false;
			
			boolean countOnly = false;

			boolean mostLiked=false;
			boolean mostDisLiked=false;
			DBObject sort1 = null;
			sort1 = new BasicDBObject("$sort", new BasicDBObject("rating", -1));
			if(compareLiked!=null)
			{
				if(compareLiked.equals("Most_Liked"))
					mostLiked=true;
				else
				{
					mostDisLiked=true;
					sort1 = new BasicDBObject("$sort", new BasicDBObject("rating", 1));
				}
					
			}

						
			if(trending!=null)
			{
				groupBy=true;
				groupByCity=true;
				sort1 = new BasicDBObject("$sort", new BasicDBObject("rating", -1));
				//reviewRating=5;
				//compareRating="EQUALS_TO";
			}
		   if(median!=null)
			{
				groupBy=true;
				groupByCity=true;
				sort1 = new BasicDBObject("$sort", new BasicDBObject("price", -1));
				//reviewRating=5;
				//compareRating="EQUALS_TO";
			}
					
			//Get the filters selected
			//Filter - Simple Search
			String[] filters = request.getParameterValues("queryCheckBox");
			//Filters - Group By
			String[] extraSettings = request.getParameterValues("extraSettings");
			String[] sortSettings = request.getParameterValues("sortSettings");

			if(sortSettings!=null)
			{
				if(sortByDropdown.equals("SORT_BY_RETAILERNAME"))
					sort1 = new BasicDBObject("$sort", new BasicDBObject("retailer", -1));
				if(sortByDropdown.equals("SORT_BY_AGE"))
					sort1 = new BasicDBObject("$sort", new BasicDBObject("age", -1));
				if(sortByDropdown.equals("SORT_BY_MANU"))
					sort1 = new BasicDBObject("$sort", new BasicDBObject("manufacturer", -1));
			}

			DBCursor dbCursor = null;
			AggregationOutput aggregateData = null;
			

			//Check for extra settings(Grouping Settings)
			if(extraSettings != null){				
				//User has selected extra settings
				groupBy = true;
				
				for(int x = 0; x <extraSettings.length; x++){
					switch (extraSettings[x]){						
						case "COUNT_ONLY":
							//Not implemented functionality to return count only
							countOnly = true;				
							break;
						case "GROUP_BY":	
							//Can add more grouping conditions here
							if(groupByDropdown.equals("GROUP_BY_CITY")){
								groupByCity = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCT")){
								groupByProduct = true;
							}else if(groupByDropdown.equals("GROUP_BY_RETAILERNAME")){
								groupByRetailerName = true;
							}else if(groupByDropdown.equals("GROUP_BY_ZIP")){
								groupByZip = true;
							} 	 					

							break;
					}		
				}				
			}			
			
			//Check the main filters only if the 'groupBy' option is not selected
		//	if(filters != null && groupBy != true){
			if(filters != null){
				for (int i = 0; i < filters.length; i++) {
					//Check what all filters are ON
					//Build the query accordingly
					switch (filters[i]){										
						case "productName":							
							filterByProduct = true;
							if(!productName.equals("ALL_PRODUCTS")){
								query.put("productName", productName);
							}						
							break;
						case "category":							
							filterByProduct = true;
							if(!productName.equals("ALL_Category")){
								query.put("category", category);
							}						
							break;				
						case "productPrice":
							filterByPrice = true;
							if (comparePrice.equals("EQUALS_TO")) {
								query.put("price", productPrice);
							}else if(comparePrice.equals("GREATER_THAN")){
								query.put("price", new BasicDBObject("$gt", productPrice));
							}else if(comparePrice.equals("LESS_THAN")){
								query.put("price", new BasicDBObject("$lt", productPrice));
							}
							break;
								
						case "age":
							filterByPrice = true;
							if (compareAge.equals("EQUALS_TO")) {
								query.put("age", age);
							}else if(compareAge.equals("GREATER_THAN")){
								query.put("age", new BasicDBObject("$gt", age));
							}else if(compareAge.equals("LESS_THAN")){
								query.put("age", new BasicDBObject("$lt", age));
							}
							break;

						case "gender":
							filterByZip = true;
							query.put("gender", gender);
							break;

						case "occupation":
							filterByZip = true;
							query.put("occupation", occupation);
							break;

						case "rebate":
							filterByZip = true;
							query.put("rebate", rebate);
							break;

						case "psale":
							filterByZip = true;
							query.put("psale", psale);
							break;

						case "retailer":							
							filterByProduct = true;
							if(!productName.equals("ALL_Retailers")){
								query.put("retailer", retailer);
							}						
							break;

						case "retailerZipcode":
							filterByZip = true;
							query.put("rzip", retailerZipcode);
							break;
												
						case "retailerCity": 
							filterByCity = true;
							if(!retailerCity.equals("All") && !groupByCity){
								query.put("city", retailerCity);
							}							
							break;
						case "retailerState": 
							filterByZip = true;
							query.put("retailerState", retailerState);
							break;								
						case "reviewRating":	
							filterByRating = true;
							if (compareRating.equals("EQUALS_TO")) {
								query.put("rating", reviewRating);
							}else{
								query.put("rating", new BasicDBObject("$gt", reviewRating));
							}
							break;
						case "reviewDate": 
							filterByZip = true;
							query.put("reviewDate", reviewDate);
							break;		
						case "reviewText": 
							filterByZip = true;
							query.put("reviewText", reviewText);
							break;						
						default:
							//Show all the reviews if nothing is selected
							noFilter = true;
							break;						
					}				
				}
			}else{
				//Show all the reviews if nothing is selected
				noFilter = true;
			}
			

			//Construct the top of the page
			constructPageTop(output);
						
			//Run the query 
			if(groupBy == true){		
				//Run the query using aggregate function
				DBObject match = null;
				DBObject groupFields = null;
				DBObject group = null;
				DBObject projectFields = null;
				DBObject project = null;
				DBObject match1 = null;
				
				DBObject limit1 = null;
				
				AggregationOutput aggregate = null;
				
				if(groupByCity){
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$city");
					//groupFields.put("maxprice", new BasicDBObject("$match", "$price"));				
					groupFields.put("maxprice", new BasicDBObject("$max", "$price"));
				//	groupFields.put("$match", new BasicDBObject("$max", "$rating"));
					groupFields.put("maxrating", new BasicDBObject("$max", "$rating"));
					//groupFields.put("sortRating", new BasicDBObject("$sort", "$rating"));
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("price", new BasicDBObject("$push", "$price"));
					//groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$rating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("maxprice", "$maxprice");
					projectFields.put("maxrating", "$maxrating");
					projectFields.put("Product", "$productName");
					projectFields.put("price", "$price");
					//projectFields.put("User", "$userName");
					//projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					
					project = new BasicDBObject("$project", projectFields);
					if(filterByRating==true)
					{
						System.out.println("truerrrr " +reviewRating);
						match1 = new BasicDBObject("$match", new BasicDBObject("rating", reviewRating));
						aggregate = myReviews.aggregate(match1,group, project);
					}
					else if(trending!=null || median!=null)
					{
						System.out.println("trend true" +returnValueDropdown);					
						aggregate = myReviews.aggregate(sort1,group,project);
					}
					else if(returnValueDropdown.equals("TOP_5") && mostLiked==true)
					{
						System.out.println("2nd true" +returnValueDropdown);
						limit1 = new BasicDBObject("$limit", 5);
						aggregate = myReviews.aggregate(sort1,limit1,group,project);
					}
					else if(returnValueDropdown.equals("TOP_5") && mostDisLiked==true)
					{
						System.out.println("2nd true" +returnValueDropdown);
						limit1 = new BasicDBObject("$limit", 5);
						aggregate = myReviews.aggregate(sort1,limit1,group,project);
					}
					else
					{
						//match1 = new BasicDBObject("$match", new BasicDBObject("price", 100));
						aggregate = myReviews.aggregate(sort1,group,project);
					}
					
					//match1 = new BasicDBObject("$max", new BasicDBObject("price",1));
					
												
					//Construct the page content
					constructGroupByCityContent(aggregate, output, countOnly);
					
					
				}else if(groupByProduct){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$productName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$rating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Product", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Products", "$productName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort1,group,project);				
							
					//Construct the page content
					constructGroupByProductContent(aggregate, output, countOnly);
					
				}	
				else if(groupByRetailerName){	
					
					//sort.put("rating",-1);
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailer");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					//groupFields.put("limit",new BasicDBObject("$limit", 5));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$rating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Product", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Products", "$productName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					sort1 = new BasicDBObject("$sort", new BasicDBObject("rating", -1));
					limit1 = new BasicDBObject("$limit", 5);
					aggregate = myReviews.aggregate(sort1,group,project);				
							
					//Construct the page content
					constructGroupByRetailerNameContent(aggregate, output, countOnly);
					
				}			
				else if(groupByZip){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$rzip");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("maxprice", new BasicDBObject("$max", "$price"));
					groupFields.put("maxrating", new BasicDBObject("$max", "$rating"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$rating"));
					groupFields.put("price", new BasicDBObject("$push", "$price"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Product", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("maxprice", "$maxprice");
					projectFields.put("maxrating", "$maxrating");
					projectFields.put("Products", "$productName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					projectFields.put("price", "$price");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort1,group,project);				
							
					//Construct the page content
					constructGroupByZipContent(aggregate, output, countOnly);
					
				}			 		  		 
			}else{
				//Check the return value selected
				int returnLimit = 0;
				
				//Create sort variable
				DBObject sort = new BasicDBObject();
												
				if (returnValueDropdown.equals("TOP_5")){
					//Top 5 - Sorted by review rating
					returnLimit = 5;
					sort.put("rating",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("TOP_10")){
					//Top 10 - Sorted by review rating
					returnLimit = 10;
					sort.put("rating",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("LATEST_5")){
					//Latest 5 - Sort by date
					returnLimit = 5;
					sort.put("reviewDate",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("LATEST_10")){
					//Latest 10 - Sort by date
					returnLimit = 10;
					sort.put("reviewDate",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else{
					//Run the simple search query(default result)
					dbCursor = myReviews.find(query);
				}		
				
				//Construct the page content
				constructDefaultContent(dbCursor, output, countOnly);
			}			
			
			//Construct the bottom of the page
			constructPageBottom(output);
			
			
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
	
	public void constructPageTop(PrintWriter output){
		String pageHeading = "Query Result";
		String myPageTop = "<!DOCTYPE html>" + "<html lang=\"en\">"
					+ "<head>	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
					+ "<title>GameZon</title>"
					+ "<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />"
					+ "</head>"
					+ "<body>"
					+ "<div id=\"container\">"
					+ "<header>"
					+ "<h1><a href=\"/\">GameZone<span></span></a></h1>"
					+ "</header>"
					+ "<nav>"
					+ "<ul>"
					+ "<li class=\"\"><a href=\"index.html\">Home</a></li>"
					+ "<li class=''><a href='/GameWebsite/Microsoft'>Microsoft</a></li>"
            		+ "<li class=''><a href='/GameWebsite/Sony'>Sony</a></li>"
            		+ "<li class='end'><a href='/GameWebsite/Nintendo'>Nintendo</a></li>"
             		+ "<li class='end'><a href='/GameWebsite/Accessories'>Accessories</a></li>"
					+ "<li class = \"start selected\"><a href=\"DataAnalytics.html\">Data Analytics</a></li>"
					+ "</ul>"
					+ "</nav>"
					+ "<div id=\"body\">"
					+ "<section id=\"review-content\">"
					+ "<article>"
					+ "<h2 style=\"color:#DE2D3A;font-weight:700;\">" +pageHeading + "</h2>";
		
		output.println(myPageTop);		
	}
	
	public void constructPageBottom(PrintWriter output){
		String myPageBottom = "</article>"
					+ "</section>"
                    + "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<footer>"
					+ "<div class=\"footer-content\">"
					+ "<ul>"
                    + "<li>"
                    + "<h4>Dummy Link Section 1</h4>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 1</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 2</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link  3</a>"
                    + "</li>"
					+ "</ul>"
					+ "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>CSP 595 - Enterprise Web Application - Assignment#3</p>"
					+ "</div>"
					+ "</footer>"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		
		output.println(myPageBottom);
	}
	
	public void constructDefaultContent(DBCursor dbCursor, PrintWriter output, boolean countOnly){
		int count = 0;
		String tableData = " ";
		String pageContent = " ";

		while (dbCursor.hasNext()) {		
			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
			tableData =  "<tr><td>Name: <b>     " + bobj.getString("productName") + " </b></td></tr>"
						 + "<tr><td>Price:       " + bobj.getInt("price") + "</br>"
						+ "Retailer:            " + bobj.getString("retailer") + "</br>"
						+ "Retailer Zipcode:    " + bobj.getString("rzip") + "</br>"
						+ "Retailer City:       " + bobj.getString("city") + "</br>"
						+ "Retailer State:      " + bobj.getString("rstate") + "</br>"
						+ "Sale:                " + bobj.getString("psale") + "</br>"
					    + "User ID:             " + bobj.getString("userName") + "</br>"
					    + "User Age:            " + bobj.getString("age") + "</br>"
						 + "User Gender:         " + bobj.getString("gender") + "</br>"
						 + "User Occupation:     " + bobj.getString("occupation") + "</br>"
						+ "Manufacturer:        " + bobj.getString("manufacturer") + "</br>"
						+ "Manufacturer Rebate: " + bobj.getString("rebate") + "</br>"
						+ "Rating:              " + bobj.getString("rating") + "</br>"
						+ "Date:                " + bobj.getString("reviewDate") + "</br>"
						+ "Review Text:         " + bobj.getString("reviewText") + "</td></tr>"
						 ;

				
			count++;
				
				output.println("<h3>"+count+"</h3>");
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
		}

		//No data found
		if(count == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
	public void constructGroupByCityContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				// BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				 BasicDBList rating = (BasicDBList) bobj.get("Rating");

				BasicDBList price = (BasicDBList) bobj.get("price");
				String maxp=bobj.getString("maxprice");
				rowCount++;
				tableData = "<tr><td><h3>City: "+bobj.getString("City")+"</h3></td>&nbsp"
						//+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>"
						+	"<td>Price max: "+bobj.getString("maxprice")+"</td>"
						+ "<td>Ratings max: "+bobj.getString("maxrating")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					String p=price.get(productCount).toString();
					System.out.println("yyyyy"+p + maxp);
					if(comparePrice.equals("MAX"))
					{
						if(p.equals(maxp))
					{
						tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
						//	+	"Review: "+productReview.get(productCount)+ "</br>"
							+"Price: "+price.get(productCount)
					+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					}
				}
				else if(trending!=null)
				{
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
						//	+	"Review: "+productReview.get(productCount)+ "</br>"
							+"Price: "+price.get(productCount)
					+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					break;
				}
				else if(median!=null)
				{int med=(productList.size()/2);
					//output.println(med);
					if(productCount==med)
					{
						tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
						//	+	"Review: "+productReview.get(productCount)+ "</br>"
							+"Price: "+price.get(productCount)
					+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					}		
					
				}
				else
				{
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
						//	+	"Review: "+productReview.get(productCount)+ "</br>"
							+"Price: "+price.get(productCount)
					+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
				}
					
					
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
	}
	
	public void constructGroupByProductContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		output.println("<h1> Grouped By - Products </h1>");
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Products");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td><h3>Product Name:"+bobj.getString("Product")+"</h3></td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (reviewCount < productReview.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(reviewCount)+"</br>"
							+   "Rating: "+rating.get(reviewCount)+"</br>"
						//	+	"Review: "+productReview.get(productCount)+ "</br>"
							//+"Price: "+price.get(productCount)
					+"</td></tr>";
							
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					reviewCount++;					
				}
					
				//Reset review count
				reviewCount = 0;
					
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}

	public void constructGroupByRetailerNameContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		output.println("<h1> Grouped By - Retailer Name </h1>");
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Products");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td><h3>Product: "+bobj.getString("Product")+"</h3></td>&nbsp";
						//+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (reviewCount < productReview.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(reviewCount)+"</br>"
							+   "Rating: "+rating.get(reviewCount)+"</br>"
						//	+	"Review: "+productReview.get(productCount)+ "</br>"
							//+"Price: "+price.get(productCount)
					+"</td></tr>";
							
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					reviewCount++;					
				}
					
				//Reset review count
				reviewCount = 0;
					
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByZipContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Zip Code </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Products");
				// BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				 BasicDBList rating = (BasicDBList) bobj.get("Rating");

				BasicDBList price = (BasicDBList) bobj.get("price");
				String maxp=bobj.getString("maxprice");
				rowCount++;
				tableData = "<tr><td><h3>Zip Code: "+bobj.getString("Product")+"</h3></td>&nbsp"
					//	+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>"
						+	"<td>Price max: "+bobj.getString("maxprice")+"</td>"
						+ "<td>Ratings max: "+bobj.getString("maxrating")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					String p=price.get(productCount).toString();
					System.out.println("yyyyy"+p + maxp);
					if(comparePrice.equals("MAX"))
					{
						if(p.equals(maxp))
					{
						tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
						//	+	"Review: "+productReview.get(productCount)+ "</br>"
							+"Price: "+price.get(productCount)
					+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					}
				}else
				{
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
						//	+	"Review: "+productReview.get(productCount)+ "</br>"
							+"Price: "+price.get(productCount)
					+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
				}
					
					
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
}
}