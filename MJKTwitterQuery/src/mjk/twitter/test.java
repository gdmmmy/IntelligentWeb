package mjk.twitter;

import java.util.ArrayList;

import mjk.foursquare.FindNearbyLocation;
import mjk.model.DatabaseConnection;
//import mjk.model.DatabaseConnection;
import mjk.model.VenuesModel;
import twitter4j.Twitter;

public class test {

	public static void main(String[] args) throws Exception {
		DatabaseConnection dc=new DatabaseConnection();
		dc.closecon();
			InitConnectionTwitter tt = new InitConnectionTwitter();
			Twitter twitterConnection=null;
			twitterConnection= tt.init();
//			String username="gdmmmy,billy322";
//			String[] usernames = new String[1];
//			usernames[0]="gdmmmy";
//
			String location="Meadowhall";
			RSETAPI rapi=new RSETAPI();
			int x=3;
//			rapi.FindLocation(x,"gdmmmy");
			FindNearbyLocation at=new FindNearbyLocation();
			ArrayList<VenuesModel>Venue=at.authenticationRequest(location);
//			for (int index=0;index<Venue.size();index++){System.out.println(Venue.get(index));}
			rapi.findTweets(twitterConnection, x, Venue);
//			int y=5;
//			FindLocationStreamingApi sa=new FindLocationStreamingApi(usernames);
			
			
//			FindTweetStreamingApi ft=new FindTweetStreamingApi(twitterConnection, Venue);
//			for (int index=0;index<Venue.size();index++){System.out.println(ft.arraystr.get(index));}
//			sa.FindLocation(twitterConnection, usernames);
			//through roadname to find tweet
			
	}
}
