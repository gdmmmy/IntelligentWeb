/**  
*DatabaseConnection
*@author Yue Ma
*@version 1.0 2014/03/24  
*/ 


package mjk.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DatabaseConnection {
	/** Database Connection Operation  
	 * @param Null 
	 * @exception sql exception
	 * @return return_value 
	 */ 
	public DatabaseConnection() throws SQLException{
	System.out.println("-------- MySQL JDBC Connection Testing ------------");
	    try{
	        Class.forName("com.mysql.jdbc.Driver");
	    }
	    catch(ClassNotFoundException e){
	        System.out.println("Can't find the MySQL JDBC Driver");
	        e.printStackTrace();
	        return;
	    }
	 
	    System.out.println("MySQL JDBC Driver Registered!");
	   
	 
	    try{
	        conn = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team002?user=team002&password=d2a7cacd");
	        
	    }catch(SQLException e){
	        System.out.println("Connection Failed! Check output console");
	        e.printStackTrace();
	        return;
	    }
	 
	    if (conn != null) {

	        System.out.println("You made it, take control your database now!");
	    } 
	    else {
	    	System.out.println("Failed to make connection!");
	    }
	}

	public void InsertUsers(String user_name, String user_screenname, String user_location, String user_pic, String user_description) throws SQLException
	{
		/** InsertUsers
		 * @param String user_name, String user_screenname, String user_location, String user_pic, String user_description 
		 * @exception sql exception
		 * @return 
		 */ 
		
		boolean check=CheckUsersByScreenname(user_screenname);
		if (check==true)//false means database do not have this record
		{
		PreparedStatement prep = conn.prepareStatement( "insert into users(user_name,user_screenname,user_location,user_pic,user_description)"
    							+ "values(?,?,?,?,?)");
    	prep.setString(1, user_name);
		prep.setString(2, user_screenname);
		prep.setString(3, user_location);
		prep.setString(4, user_pic);
		prep.setString(5, user_description);
		prep.executeUpdate();
		}
		else { return; };
	}
	
	public void CheckUsers() throws SQLException
	{
		/** CheckUsers
		 * @param 
		 * @exception sql exception
		 * @return 
		 */
		PreparedStatement prep = 
				conn.prepareStatement("select * from users");
		
			ResultSet rst = prep.executeQuery();
			
			while(rst.next()){
				System.out.println(rst.getString("user_screenname"));
				System.out.println(rst.getString("user_name"));
				System.out.println(rst.getString("user_location"));
				System.out.println(rst.getString("user_pic"));
				System.out.println(rst.getString("user_description"));
				
			}
	}
	
	public boolean CheckUsersByScreenname(String user_screenname) throws SQLException
	{
		/** CheckUsersByScreenname
		 * @param user_screenname
		 * @exception sql exception
		 * @return true if user exist
		 */
		boolean checkid=true;
		
		PreparedStatement prep = 
				conn.prepareStatement("select * from users where user_screenname=?");
			prep.setString(1, user_screenname);
			ResultSet rst = prep.executeQuery();
//			System.out.println(rst.wasNull());//if there have value inside, this should be false
			if(rst.wasNull()==false)
			{
			while(rst.next()){
//				System.out.println(rst.getString("user_id"));
//				System.out.println(rst.getString("user_name"));
//				System.out.println(rst.getString("user_sid"));
//				System.out.println(rst.getString("user_pic"));
//				System.out.println(rst.getTimestamp("user_pic"));
				checkid=false;
			}
			
			}
			else{
			checkid=true;//insert ok
			}
			return checkid;
	}
	
	public void UpdateUsers(String screenname, String user_name, String user_screenname, String user_location, String user_pic, String user_description) throws SQLException 
	{
		/** UpdateUsers
		 * @param String screenname, String user_name, String user_screenname, String user_location, String user_pic, String user_description
		 * @exception sql exception
		 * @return 
		 */
		PreparedStatement prep = 
				conn.prepareStatement("update users set user_name=?,user_screenname=?,user_location=?,user_pic=?,user_description=? where user_screenname=?");

			prep.setString(1, user_name);
			prep.setString(2, user_screenname);
			prep.setString(3, user_pic);
			prep.setString(4, user_description);
			prep.setString(5, screenname);
			
			prep.executeUpdate();
            
	}
	
	public void DeleteUsers() throws SQLException
	{
		/** DeleteUsers
		 * @param 
		 * @exception sql exception
		 * @return 
		 */
		PreparedStatement prep=conn.prepareStatement(
				"delete from users");
		
		prep.executeUpdate();
		 
	}
	
	public void DeleteUsersByScreenname(String user_screenname) throws SQLException
	{
		/** DeleteUsers
		 * @param usser_screenname
		 * @exception sql exception
		 * @return 
		 */
		PreparedStatement prep=conn.prepareStatement(
				"delete from users where user_sid=?");
		prep.setString(1, user_screenname);
		prep.executeUpdate();
        
	}
	
	public void InsertTweets(String tweet_userscreenname, String tweet_content, String tweet_time, 
			String tweet_sid, String tweet_isretweet) throws SQLException
	{   
		/** InsertTweets
		 * @param String tweet_userscreenname, String tweet_content, String tweet_time, 
			String tweet_sid, String tweet_isretweet
		 * @exception sql exception
		 * @return false if data exist
		 */
		boolean check=CheckTweetsById(tweet_sid);
		if (check==true)
		{
		PreparedStatement prep = conn.prepareStatement( "insert into tweets(tweet_userscreenname,tweet_content,"
													+ "tweet_time,tweet_sid,tweet_isretweet)"+ "values(?,?,?,?,?)");
    	prep.setString(1, tweet_userscreenname);
		prep.setString(2, tweet_content);
		prep.setString(3, tweet_time);
		prep.setString(4, tweet_sid);
		prep.setString(5, tweet_isretweet);
		
		prep.executeUpdate();
 		}
		else { return; };
	}
	
	public void CheckTweets() throws SQLException
	{
		/** CheckTweets
		 * @param 
		 * @exception sql exception
		 * @return 
		 */
		PreparedStatement prep = 
				conn.prepareStatement("select * from tweets");
		
			ResultSet rst = prep.executeQuery();
			
			while(rst.next()){
				System.out.println(rst.getString("tweet_userscreenname"));
				System.out.println(rst.getString("tweet_content"));
				System.out.println(rst.getString("tweet_time"));
				System.out.println(rst.getString("tweet_sid"));
				System.out.println(rst.getString("tweet_isretweet"));

			}
			 ;
	}
	
	public boolean CheckTweetsById(String tweet_sid) throws SQLException
	{
		/** CheckTweetsById
		 * @param 
		 * @exception sql exception
		 * @return false if data exist
		 */
		boolean checkid=true;		
		PreparedStatement prep = 
				conn.prepareStatement("select * from tweets where tweet_sid=?");
			prep.setString(1, tweet_sid);
			ResultSet rst = prep.executeQuery();
//			System.out.println(rst.wasNull());//if there have value inside, this should be false
			if(rst.wasNull()==false)
			{
				while(rst.next()){
//				System.out.println(rst.getString("tweet_usersid"));
//				System.out.println(rst.getString("tweet_content"));
//				System.out.println(rst.getString("tweet_time"));
//				System.out.println(rst.getString("tweet_sid"));
//				System.out.println(rst.getString("tweet_isretweet"));
				checkid=false;//insert not ok
				}
			}
			else{
			checkid=true;//insert ok
			}
			return checkid;
	}
	
	
	public void CheckTweetsByUserscreenname(String tweet_userscreenname) throws SQLException
	{
		/** CheckTweetsByUserscreenname
		 * @param 
		 * @exception sql exception
		 * @return 
		 */
		PreparedStatement prep = 
				conn.prepareStatement("select * from tweets where tweet_userscreenname=?");
			prep.setString(1, tweet_userscreenname);
			ResultSet rst = prep.executeQuery();
			while(rst.next()){
				System.out.println(rst.getString("tweet_userscreenname"));
				System.out.println(rst.getString("tweet_content"));
				System.out.println(rst.getString("tweet_time"));
				System.out.println(rst.getString("tweet_sid"));
				System.out.println(rst.getString("tweet_isretweet"));
			}
	}
	
	public void UpdateTweets(String sid,String tweet_userscreenname, String tweet_content, String tweet_time, 
			String tweet_sid, String tweet_isretweet) throws SQLException 
	{
		/** UpdateTweets
		 * @param String sid,String tweet_userscreenname, String tweet_content, String tweet_time, 
			String tweet_sid, String tweet_isretweet
		 * @exception sql exception
		 * @return 
		 */
		PreparedStatement prep = 
				conn.prepareStatement("update tweets set tweet_userscreenname=?,tweet_content=?,tweet_time=?,tweet_sid=?,"
						+ "tweet_isretweet=? where tweet_sid=?");

			prep.setString(1, tweet_userscreenname);
			prep.setString(2, tweet_content);
			prep.setString(3, tweet_time);
			prep.setString(4, tweet_sid);
			prep.setString(5, tweet_isretweet);
			prep.setString(6, sid);
			prep.executeUpdate();
	}
	
	public void DeleteTweets() throws SQLException
	{
		/** DeleteTweets
		 * @param 
		 * @exception sql exception
		 * @return 
		 */
		PreparedStatement prep=conn.prepareStatement(
				"delete from tweets");
		
		prep.executeUpdate();
		 
	}
	
	public void DeleteTweetsById(String tweet_sid) throws SQLException
	{
		/** DeleteTweetsById
		 * @param tweet_sid
		 * @exception sql exception
		 * @return 
		 */
		PreparedStatement prep=conn.prepareStatement(
				"delete from users where tweet_sid=?");
		prep.setString(1, tweet_sid);
		prep.executeUpdate();
	}
	
	 Connection conn = null;
	 public void closecon() throws SQLException{
		 conn.close();
	 }
	 
	 public void InsertVenue(VenuesModel vm) throws SQLException{
		 /** InsertVenue
			 * @param vm
			 * @exception sql exception
			 * @return 
			 */
		 boolean check=CheckVenueById(vm.getLatitude(),vm.getLongitude());
			if (check==true)//false means database do not have this record
			{
			PreparedStatement prep = conn.prepareStatement( "insert into venues(venue_name,venue_pic,venue_category,"
					+ "venue_lattitude,venue_longitude,venue_url,"
					+ "venue_description)"
	    							+ "values(?,?,?,?,?,?,?)");
			String lo=String.valueOf(vm.getLongitude());
			String la=String.valueOf(vm.getLatitude());
			
	    	prep.setString(1, vm.getName());
			prep.setString(2, vm.getphoto());
			
			prep.setString(3, vm.getcategory());
			prep.setString(4, la);
			prep.setString(5, lo);
			prep.setString(6, vm.getphoto());
			prep.setString(7, null);
//			prep.setString(8, ti);
			prep.executeUpdate();
			}
			else { return; };
	 }
	 public boolean CheckVenueById(double longitude, double latitude) throws SQLException
		{
		 /** CheckVenueById
			 * @param double longitude, double latitude
			 * @exception false if data exist
			 * @return 
			 */
			boolean checkid=true;	
			String la=String.valueOf(latitude);
			String lo=String.valueOf(longitude);
			PreparedStatement prep = 
					conn.prepareStatement("select * from venues where venue_lattitude=? and venue_longitude=?");
				prep.setString(1,la);
				prep.setString(2, lo);
				ResultSet rst = prep.executeQuery();
				System.out.println(rst.wasNull());//if there have value inside, this should be false
				if(rst.wasNull()==false)
				{
					while(rst.next()){
////					System.out.println(rst.getString("tweet_usersid"));
//					System.out.println(rst.getString("tweet_content"));
//					System.out.println(rst.getString("tweet_time"));
//					System.out.println(rst.getString("tweet_sid"));
//					System.out.println(rst.getString("tweet_isretweet"));
					checkid=false;//insert not ok
					}
				}
				else{
				checkid=true;//insert ok
				}
				return checkid;
		}
	 public void closeconn() throws SQLException{
		 /** closeconn
			 * @param 
			 * @exception false if data exist
			 * @return 
			 */
		 conn.close();
	 }
	 public void InsertContacts(String contact_userscreenname, String contact_personscreenname) throws SQLException
	 {
		 /** InsertContacts
			 * @param String contact_userscreenname, String contact_personscreenname
			 * @exception 
			 * @return 
			 */
			boolean check=CheckContactsByName(contact_userscreenname,contact_personscreenname);
			if (check==true)//false means database do not have this record
			{
			PreparedStatement prep = conn.prepareStatement( "insert into contacts(contact_userscreenname,contact_personscreenname)"
														+ "values(?,?)");
	    	prep.setString(1, contact_userscreenname);
			prep.setString(2, contact_personscreenname);

			prep.executeUpdate();
			}
	 }
			
	public boolean CheckContactsByName(String contact_userscreenname, String contact_personscreenname) throws SQLException
	 {
		/** CheckContactsByName
		 * @param String contact_userscreenname, String contact_personscreenname
		 * @exception false if data exist
		 * @return 
		 */
		boolean checkid=true;		
		PreparedStatement prep = 
				conn.prepareStatement("select * from contacts where contact_userscreenname=? and contact_personscreenname=?");
			prep.setString(1, contact_userscreenname);
			prep.setString(2, contact_personscreenname);
			ResultSet rst = prep.executeQuery();

			if(rst.wasNull()==false)
			{
				while(rst.next()){			
				checkid=false;//insert not ok
				}
			}
			else{
			checkid=true;//insert ok
			}
			return checkid;
	 }
	
	
	public void CheckContacts() throws SQLException
	{
		/** CheckContacts
		 * @param 
		 * @exception
		 * @return 
		 */
		PreparedStatement prep = 
				conn.prepareStatement("select * from contacts");
		
			ResultSet rst = prep.executeQuery();
			
			while(rst.next()){
				System.out.println(rst.getString("contact_userscreenname"));
				System.out.println(rst.getString("contact_personscreenname"));

			}
	}
	
	public void DeleteContacts() throws SQLException
	{
		/** DeleteContacts
		 * @param 
		 * @exception SQLException
		 * @return 
		 */
		PreparedStatement prep=conn.prepareStatement(
				"delete from contacts");
		
		prep.executeUpdate();
		 
	}

}