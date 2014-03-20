package mjk.query.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mjk.model.DatabaseConnection;
import mjk.twitter.InitConnectionTwitter;
import mjk.twitter.RSETAPI;
import twitter4j.Twitter;
import fi.foyt.foursquare.api.FoursquareApiException;

/**
 * Servlet implementation class QueryInput
 */
public class QueryLocationFromTweets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryLocationFromTweets() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		 TODO Auto-generated method stub
//		 Actual logic goes here.

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//			String user=request.getParameter("user");
//			String xday=request.getParameter("day");
//		set two parameter to get the value from webpage, set them as the 
//		init value of class
		// System.out.println(request.getParameter("keywords"));
		// response.setContentType("text/plain;charset=utf-8");
		// request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
				DatabaseConnection dc=new DatabaseConnection();
				dc.closecon();
				InitConnectionTwitter tt = new InitConnectionTwitter();
				Twitter twitterConnection=tt.init();
				RSETAPI rapi=new RSETAPI();
				//if(x=0) 
				//TODO
				//FindLocationStreamingApi flsa=new FindLocationStreamingApi();
				//need add transfer streamingapi json to this method
				int x=3;
				String json_data=rapi.FindLocation(x,"gdmmmy");
				//get the data from webpage and set with x and gdmmmy
				response.setContentType("tweetvenue/json");
				response.setCharacterEncoding("UTF-8");
				out.write(json_data);
		} catch (IOException e) {
			System.out.println("Something wrong");

			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FoursquareApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
