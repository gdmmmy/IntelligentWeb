package mjk.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import twitter4j.User;

import com.mysql.jdbc.Statement;
public class DatabaseConnection {
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

	public void InsertUsers(String user_name, String user_sid, String user_pic, String user_description) throws SQLException
	{
		boolean check=CheckUsersById(user_sid);
		if (check==true)//false means database do not have this record
		{
		PreparedStatement prep = conn.prepareStatement( "insert into users(user_name,user_sid,user_pic,user_description)"
    							+ "values(?,?,?,?)");
    	prep.setString(1, user_name);
		prep.setString(2, user_sid);
		prep.setString(3, user_pic);
		prep.setString(4, user_description);
		prep.executeUpdate();
		}
		else { return; };
	}
	
	public void CheckUsers() throws SQLException
	{
		PreparedStatement prep = 
				conn.prepareStatement("select * from users");
		
			ResultSet rst = prep.executeQuery();
			
			while(rst.next()){
				System.out.println(rst.getString("user_id"));
				System.out.println(rst.getString("user_name"));
				System.out.println(rst.getString("user_sid"));
				System.out.println(rst.getString("user_pic"));
				
			}
	}
	
	public boolean CheckUsersById(String user_sid) throws SQLException
	{
		boolean checkid=true;
		
		PreparedStatement prep = 
				conn.prepareStatement("select * from users where user_sid=?");
			prep.setString(1, user_sid);
			ResultSet rst = prep.executeQuery();
			if(rst.wasNull()==false)
			{
			while(rst.next()){
				System.out.println(rst.getString("user_id"));
				System.out.println(rst.getString("user_name"));
				System.out.println(rst.getString("user_sid"));
				System.out.println(rst.getString("user_pic"));
//				System.out.println(rst.getTimestamp("user_pic"));
			}
			}
			else{
			checkid=true;//insert ok
			}
			return checkid;
	}
	
	public void UpdateUsers(String sid, String user_name, String user_sid, String user_pic, String user_description) throws SQLException 
	{
		PreparedStatement prep = 
				conn.prepareStatement("update users set user_name=?,user_sid=?,user_pic=?,user_description=? where user_sid=?");

			prep.setString(1, user_name);
			prep.setString(2, user_sid);
			prep.setString(3, user_pic);
			prep.setString(4, user_description);
			prep.setString(5, sid);
			
			prep.executeUpdate();
            
			 ;
	}
	
	public void DeleteUsers() throws SQLException
	{
		PreparedStatement prep=conn.prepareStatement(
				"delete from users");
		
		prep.executeUpdate();
		 ;
	}
	
	public void DeleteUsersById(String user_sid) throws SQLException
	{
		PreparedStatement prep=conn.prepareStatement(
				"delete from users where user_sid=?");
		prep.setString(1, user_sid);
		prep.executeUpdate();
        
		 ;
	}
	
	public void InsertTweets(String tweet_usersid, String tweet_content, String tweet_time, 
			String tweet_sid, String tweet_isretweet) throws SQLException
	{   
		boolean check=CheckTweetsById(tweet_sid);
		if (check==true)//false means database do not have this record
		{
		PreparedStatement prep = conn.prepareStatement( "insert into tweets(tweet_usersid,tweet_content,"
													+ "tweet_time,tweet_sid,tweet_isretweet)"+ "values(?,?,?,?,?)");
    	prep.setString(1, tweet_usersid);
		prep.setString(2, tweet_content);
		prep.setString(3, tweet_time);
		prep.setString(4, tweet_sid);
		prep.setString(5, tweet_isretweet);
		
		prep.executeUpdate();
		
//		 ;
		}
		else { return; };
	}
	
	public void CheckTweets() throws SQLException
	{
		PreparedStatement prep = 
				conn.prepareStatement("select * from tweets");
		
			ResultSet rst = prep.executeQuery();
			
			while(rst.next()){
				System.out.println(rst.getString("tweet_usersid"));
				System.out.println(rst.getString("tweet_content"));
				System.out.println(rst.getString("tweet_time"));
				System.out.println(rst.getString("tweet_sid"));
				System.out.println(rst.getString("tweet_isretweet"));

			}
			 ;
	}
	
	public boolean CheckTweetsById(String tweet_sid) throws SQLException
	{
		boolean checkid=true;		
		PreparedStatement prep = 
				conn.prepareStatement("select * from tweets where tweet_sid=?");
			prep.setString(1, tweet_sid);
			ResultSet rst = prep.executeQuery();
			System.out.println(rst.wasNull());//if there have value inside, this should be false
			if(rst.wasNull()==false)
			{
				while(rst.next()){
				System.out.println(rst.getString("tweet_usersid"));
				System.out.println(rst.getString("tweet_content"));
				System.out.println(rst.getString("tweet_time"));
				System.out.println(rst.getString("tweet_sid"));
				System.out.println(rst.getString("tweet_isretweet"));
				checkid=false;//insert not ok
				}
			}
			else{
			checkid=true;//insert ok
			}
			return checkid;
	}
	
	
	public void CheckTweetsByUserid(String tweet_usersid) throws SQLException
	{
		PreparedStatement prep = 
				conn.prepareStatement("select * from tweets where tweet_usersid=?");
			prep.setString(1, tweet_usersid);
			ResultSet rst = prep.executeQuery();
			while(rst.next()){
				System.out.println(rst.getString("tweet_usersid"));
				System.out.println(rst.getString("tweet_content"));
				System.out.println(rst.getString("tweet_time"));
				System.out.println(rst.getString("tweet_sid"));
				System.out.println(rst.getString("tweet_isretweet"));
			}
	}
	
	public void UpdateTweets(String sid,String tweet_usersid, String tweet_content, String tweet_time, 
			String tweet_sid, String tweet_isretweet) throws SQLException 
	{
		PreparedStatement prep = 
				conn.prepareStatement("update tweets set tweet_usersid=?,tweet_content=?,tweet_time=?,tweet_sid=?,"
						+ "tweet_isretweet=? where tweet_sid=?");

			prep.setString(1, tweet_usersid);
			prep.setString(2, tweet_content);
			prep.setString(3, tweet_time);
			prep.setString(4, tweet_sid);
			prep.setString(5, tweet_isretweet);
			prep.setString(6, sid);
			prep.executeUpdate();
	}
	
	public void DeleteTweets() throws SQLException
	{
		PreparedStatement prep=conn.prepareStatement(
				"delete from tweets");
		
		prep.executeUpdate();
		 
	}
	
	public void DeleteTweetsById(String tweet_sid) throws SQLException
	{
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
//		 boolean check=CheckVenueById(vm.getLatitude(),vm.getLongtitude());
//			if (check==true)//false means database do not have this record
//			{
			PreparedStatement prep = conn.prepareStatement( "insert into venues(venue_name,venue_pic,venue_category,"
					+ "venue_lattitude,venue_longitude,venue_url,"
					+ "venue_description,venue_created)"
	    							+ "values(?,?,?,?,?,?,?,?)");
			String lo=String.valueOf(vm.getLongtitude());
			String la=String.valueOf(vm.getLatitude());
			String ti=String.valueOf(vm.getcreattime());
	    	prep.setString(1, vm.getName());
			prep.setString(2, vm.getphoto());
			prep.setString(3, vm.getcategory());
			prep.setString(4, la);
			prep.setString(5, lo);
			prep.setString(6, vm.getphoto());
			prep.setString(7, null);
			prep.setString(8, ti);
			prep.executeUpdate();
//			}
//			else { return; };
	 }
	 public boolean CheckVenueById(double longtitude, double latitude) throws SQLException
		{
			boolean checkid=true;	
			String la=String.valueOf(latitude);
			String lo=String.valueOf(longtitude);
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

}
