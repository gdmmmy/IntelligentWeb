
/**  
*Demo--QueryDiscussion
*@author Jing Sizhe 
*@version 1.0 24/3/204  
*/ 

package mjk.query.servlet;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import twitter4j.Twitter;
import mjk.twitter.*;
/** 
* Servlet for discussion queries   
* @author Jing Sizhe, Team MJK
* @version 2.0, May 2014
*/
public class QueryDiscussion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryDiscussion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	// protected class which calls specific_discussion class with the given variables
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String jsonpack = "This is a test";
		ArrayList<String> JsonArray = new ArrayList();
		System.out.println("!");
//		System.out.println(request.getContentType());
		PrintWriter out = response.getWriter();
		
		String keywords=request.getParameter("keyword");
		String latitude=request.getParameter("latitude");
		String longitude=request.getParameter("longitude");
		
		Specific_discussion tt = new Specific_discussion();
		
		InitConnectionTwitter ti = new InitConnectionTwitter();
	
		Twitter twitterConnection = null;
		
		try {
			
			twitterConnection= ti.init();
			
			jsonpack=tt.getSimpleTimeLine(twitterConnection,keywords,latitude,longitude);
			
			System.out.println(JsonArray);
			} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
			}
		
		response.setContentType("discussion/json");
		response.setCharacterEncoding("UTF-8");
		out.write(jsonpack);
	}
}
