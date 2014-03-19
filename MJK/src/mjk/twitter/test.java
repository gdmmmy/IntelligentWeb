package mjk.twitter;

import java.util.ArrayList;

import mjk.foursquare.FindNearbyLocation;
import mjk.model.VenuesModel;
import twitter4j.Twitter;

public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
			InitConnectionTwitter tt = new InitConnectionTwitter();
			Twitter twitterConnection=null;
			twitterConnection= tt.init();
//			String username="gdmmmy,billy322";
			String[] usernames = new String[1];
			usernames[0]="gdmmmy";

			String location="sheffield";
			RSETAPI rapi=new RSETAPI();
			int x=3;
			
			rapi.FindLocation(x,"gdmmmy");
			//through username to find location
			FindNearbyLocation at=new FindNearbyLocation();
			ArrayList<VenuesModel>Venue=at.authenticationRequest(location);
			rapi.findTweets(twitterConnection, x, Venue);
//			int y=5;
//			FindLocationStreamingApi sa=new FindLocationStreamingApi(usernames);
//			FindTweetStreamingApi ft=new FindTweetStreamingApi(twitterConnection, Venue);
			
//			sa.FindLocation(twitterConnection, usernames);
			//through roadname to find tweet
	}
}
