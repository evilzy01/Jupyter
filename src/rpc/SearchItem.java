package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.TicketMasterAPI;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
//		1. Return Response
//		PrintWriter out = response.getWriter();		
//		if (request.getParameter("username") != null) {
//			String username = request.getParameter("username");
//			out.print("Hello " + username);
//		}
//		out.close();
		
//		2. Return HTML
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<html><body>");
//		out.println("<h1> This is a HTML page</h1>");
//		out.println("</body></html>");
//		out.close();
		
//		3. Return JSON
//		response.setContentType("application/json");
//		PrintWriter out = response.getWriter();
//		String username = "";
//		if (request.getParameter("username") != null) {
//			username = request.getParameter("username");
//		}
//		JSONObject obj = new JSONObject();
//		try {
//			obj.put("username", username);
//		}catch(JSONException e) {
//			e.printStackTrace();
//		}
//		out.print(obj);
//		out.close();
		
//		4. Return JSON Array
//		response.setContentType("application/json");
//		PrintWriter out = response.getWriter();
//		JSONArray array = new JSONArray();
//				
//		try {
//			array.put(new JSONObject().put("username", "abcd"));
//			array.put(new JSONObject().put("username", "zoey"));
//		}catch(JSONException e) {
//			e.printStackTrace();
//		}
//		out.print(array);
//		out.close();
		
//		5. Return JSON Array using RpcHelper
//		JSONArray array = new JSONArray();	
//		try {
//			array.put(new JSONObject().put("username", "abc"));
//			array.put(new JSONObject().put("username", "zoey"));
//		}catch(JSONException e) {
//			e.printStackTrace();
//		}
//		RpcHelper.writeJsonArray(response, array);
		
//		6. Return JSON Array of all the information searched
		JSONArray jsArray = new JSONArray();
		try {
			String userId = request.getParameter("user_id");
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lon = Double.parseDouble(request.getParameter("lon"));
			// Term can be empty or null.
			String keyword = request.getParameter("term");

////			 Version 1: before building dbConnection, directly get data from API
//			TicketMasterAPI tmAPI = new TicketMasterAPI();
//			List<Item> items = tmAPI.search(lat, lon, keyword);
			
			// Version 2: after building dbConnection, set a mySQLConnection
			DBConnection connection = DBConnectionFactory.getConnection();
			List<Item> items = connection.searchItems(lat, lon, keyword); 		
			Set<String> favorite = connection.getFavoriteItemIds(userId);
			connection.close();
			
			for (Item item : items) {
				JSONObject obj = item.toJSONObject();
				// Check if this is a favorite one.
				// This field is required by frontend to correctly display favorite items.
				obj.put("favorite", favorite.contains(item.getItemId()));
				jsArray.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		RpcHelper.writeJsonArray(response, jsArray);
		
	}
		

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}