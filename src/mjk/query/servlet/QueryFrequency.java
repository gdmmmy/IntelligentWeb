
package mjk.query.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mjk.twitter.InitConnectionTwitter;
import mjk.twitter.Specific_user;
import twitter4j.Twitter;

/** 
* Servlet for frequency queries   
* @author Jing Sizhe, Team MJK
* @version 2.0, May 2014
*/
public class QueryFrequency extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryFrequency() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	// protected class which calls specific_user class with the given variables
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("!!!!");
		String number = request.getParameter("xkeys");
		String days = request.getParameter("xdays");
		String username = request.getParameter("user");
		int n = Integer.parseInt(number);
		int d = Integer.parseInt(days);
		
		//System.out.println("1");
		getServletContext();
		//int n =5;
		//int d =5;
		//String username = "153826580,testingjing,gdmmmy,Terry4J";
		String jsonpack;
		Specific_user sc = new Specific_user();
		
		InitConnectionTwitter ti = new InitConnectionTwitter();
	
		Twitter twitterConnection = null;
		
		try {
			
			twitterConnection= ti.init();
			
			jsonpack=sc.getspecificuser(twitterConnection, n, d, username, getServletContext());
			response.setContentType("frequency/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonpack);
			System.out.println(jsonpack);
			} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
			}
//		PrintWriter out = response.getWriter();
		
//		
		
	}

}
