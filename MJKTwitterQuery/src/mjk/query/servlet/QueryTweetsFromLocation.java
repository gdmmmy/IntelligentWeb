/** Sevelet for query tweets from nearby location
* @param Null 
* @exception   
* @return return_value 
*/
package mjk.query.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mjk.foursquare.FindNearbyLocation;
import mjk.model.DatabaseConnection;
import mjk.model.VenuesModel;
import mjk.twitter.FindTweetStreamingApi;
import mjk.twitter.InitConnectionTwitter;
import mjk.twitter.RSETAPI;
import twitter4j.Twitter;
import fi.foyt.foursquare.api.FoursquareApiException;

/**
 * Servlet implementation class QueryLocationFromTweets
 */
public class QueryTweetsFromLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryTweetsFromLocation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
					String venue=request.getParameter("venue");
				    String xday=request.getParameter("xdays");
				    PrintWriter out = response.getWriter();
				    int x=Integer.parseInt(xday);
				    FindNearbyLocation at=new FindNearbyLocation();
					String location=venue;
				    ArrayList<VenuesModel> Venue=new ArrayList<VenuesModel>();
					try {
						Venue = at.authenticationRequest(location);
						DatabaseConnection dc=new DatabaseConnection();
						dc.closecon();
						} 
					catch (FoursquareApiException | SQLException e1) {
						e1.printStackTrace();
					}
					InitConnectionTwitter tt = new InitConnectionTwitter();
					Twitter twitterConnection=null;
					try {
						twitterConnection= tt.init();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				    if (x!=0){
				    		RSETAPI rapi=new RSETAPI();
				    		String json_data;
							try {
								json_data = rapi.findTweets(twitterConnection, x, Venue);
								response.setContentType("uservenue/json");
								response.setCharacterEncoding("UTF-8");
				    		out.write(json_data);
							} catch (SQLException e) {
								e.printStackTrace();
							}
				    }else{
				    		try {
								FindTweetStreamingApi ft=new FindTweetStreamingApi(twitterConnection, Venue);
							} catch (Exception e) {
									e.printStackTrace();
							}
				    	}
	}

}
