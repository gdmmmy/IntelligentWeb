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
				//	String venue=request.getParameter("venue")
				// String xday=request.getParameter("xday");
				// response.setContentType("text/plain;charset=utf-8");
				// request.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				try {
						DatabaseConnection dc=new DatabaseConnection();
						dc.closecon();
						InitConnectionTwitter tt = new InitConnectionTwitter();
						Twitter twitterConnection=null;
						twitterConnection= tt.init();
//						String username="gdmmmy,billy322";
						String[] usernames = new String[1];
//						usernames[0]="gdmmmy";
						FindNearbyLocation at=new FindNearbyLocation();
						String location="2 Westfield Terrace";
						RSETAPI rapi=new RSETAPI();
						int x=3;
						//should consider 
						ArrayList<VenuesModel>Venue=at.authenticationRequest(location);
						String json_data=rapi.findTweets(twitterConnection, x, Venue);
						response.setContentType("user/venue/json");
						response.setCharacterEncoding("UTF-8");
						out.write(json_data);
				} catch (IOException e) {
					System.out.println("Something wrong");

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
