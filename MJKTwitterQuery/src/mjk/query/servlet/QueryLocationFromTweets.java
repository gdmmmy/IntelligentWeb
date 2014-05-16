/** Sevelet for query Location for specific user
* @param Null 
* @exception   
* @return return_value 
*/
package mjk.query.servlet;
//servlet for query venue
//2b
//input user and xdays
//output list of tweets and venus
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mjk.model.DatabaseConnection;
import mjk.twitter.FindLocationStreamingApi;
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
			String username=request.getParameter("user");
			String xday=request.getParameter("xdays");
			PrintWriter out = response.getWriter();
			int x=Integer.parseInt(xday);
			if(x!=0){
				try {
					InitConnectionTwitter tt = new InitConnectionTwitter();
					Twitter twitterConnection=tt.init();
					RSETAPI rapi=new RSETAPI();
					String json_data=rapi.FindLocation(x,username);
					response.setContentType("tweetvenue/json");
					response.setCharacterEncoding("UTF-8");
					out.write(json_data);
				} catch (IOException e) {
					System.out.println("Something wrong");
					return;
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (FoursquareApiException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
		}
	}
	
	else{
			String[] usernames={username};
			try {
				FindLocationStreamingApi sa=new FindLocationStreamingApi(usernames);
				if (sa.arraystr!=null)
				{
					String json_data=sa.arraystr.get(0);
					System.out.println(sa.arraystr.get(0));
					response.setContentType("tweetvenue/json");
					response.setCharacterEncoding("UTF-8");
					out.write(json_data);
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
