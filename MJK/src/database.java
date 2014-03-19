import java.sql.SQLException;

import mjk.model.DatabaseConnection;


public class database {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		DatabaseConnection dc=new DatabaseConnection();
//		dc.CheckTweets();
		dc.CheckUsers();
		dc.closecon();
	}

}
