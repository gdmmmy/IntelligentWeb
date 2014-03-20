package mjk.webpage.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import mjk.*;
import mjk.twitter.RSETAPI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Query
 */
public class Query extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Query() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// reading the user input
		request.setCharacterEncoding("gb2312");
		String user = request.getParameter("text");
		String XXXX = "Welcome you----you Evening, "+user;
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>");
		out.println("Welcome Page123456");
		out.println("</title></head>");
		out.println("<body>");
		out.println(XXXX);
		out.println("</body></html>");
		out.close();
	    RSETAPI RS=new RSETAPI();
	    
	    
	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
