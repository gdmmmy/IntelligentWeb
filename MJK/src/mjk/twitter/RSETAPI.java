package mjk.twitter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mjk.foursquare.ConnectWithTwitter;
import mjk.model.DatabaseConnection;
import mjk.model.JsonTweetsOperation;
import mjk.model.JsonUserOperation;
import mjk.model.JsonVenuesOperation;
import mjk.model.TweeterModel;
import mjk.model.UserModel;
import mjk.model.VenuesModel;
import twitter4j.*;
import fi.foyt.foursquare.api.FoursquareApiException;

public class RSETAPI {

	private String shorturl;
	
	private List<TweeterModel> tms = new ArrayList<TweeterModel>();
	private List<VenuesModel>vms=new ArrayList<VenuesModel>();
	private VenuesModel vm;
	//According to specific user to find his/her twitter and then connect with foursquare to check venue
	public String FindLocation(int K,String username) throws FoursquareApiException, SQLException{
		DatabaseConnection dc=new DatabaseConnection();
		
        try {
        		InitConnectionTwitter tt = new InitConnectionTwitter();
        		Twitter twitterConnection=null;
        		twitterConnection= tt.init();
        		Query query= new Query("from:"+username);
        		QueryResult result = twitterConnection.search(query);
        		List<Status> tweets = result.getTweets();
      for (Status tweet : tweets) {///gets the user
    	  		User user = tweet.getUser();
    	  		TweeterModel usertweet=new TweeterModel();
    	  		long time=tweet.getCreatedAt().getTime();
    	  		int day =(int) (System.currentTimeMillis()-time)/1000/3600/24;
//    	  		System.out.println(user.getName());
//  				System.out.println(tweet.getText());
//  				dc.InsertTweets(user.getName(), tweet.getText(), null, null, null);
    	  		if (day<K){
    		  	if(tweet.getURLEntities()!=null)
    		  		{
    		  			URLEntity[] urls = tweet.getURLEntities();
    		  			for(URLEntity url : urls){
    		  				shorturl=url.getURL();
    		  				ConnectWithTwitter square=new ConnectWithTwitter();
    		  				vm=square.ConnectTwitter(shorturl);
//    		  				System.out.println("namenamenamenamenamenamenamename"+vm.getName());
//    		  				dc.InsertVenue(vm);
    		  				usertweet.settime(tweet.getCreatedAt());
    		  				usertweet.settweetcontext(tweet.getText());
    		  				usertweet.setuserhandle(user.getName());
    		  				String tweettime=tweet.getCreatedAt().toGMTString();
    		  				String tweetid=Long.toString(tweet.getId());
    		  				vms.add(vm);
    		  				String sid=String.valueOf(tweet.getId());
    		  				String id=String.valueOf(user.getId());
//    		  				dc.InsertTweets(id,tweet.getText(), " ",user.getScreenName() , "1");
    		  				
    		  				//add usertweet as a object
		  		        }
 		  		}
    	  }
    	  else{};
    	  tms.add(usertweet);
//    	  dc.closecon();
      } 
}
        catch (Exception te) {
        		te.printStackTrace();
        		System.out.println("Failed to search tweets:" +te.getMessage());
        		System.exit(-1);
        }
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("tweetsinfo", tms);
        	JsonTweetsOperation ujson=new JsonTweetsOperation();
//        JsonVenuesOperation vjson=new JsonVenuesOperation();
        	map.put("venues:", vms);
        	String testjson=ujson.JsonGenerate((HashMap<String, Object>) map);
        	System.out.println(testjson);
        	return testjson;
	}

public String findTweets(Twitter twitter,int K,List<VenuesModel> Location) throws SQLException{
	DatabaseConnection dc=new DatabaseConnection();
	ArrayList<UserModel> usrs=new ArrayList();
	try {
			int index=0;
			for(index=0;index<Location.size();index++){
				Query query= new Query();
				query.setGeoCode(new GeoLocation(Location.get(index).getLatitude(),
						Location.get(index).getLongtitude())
				,2,query.KILOMETERS);
				QueryResult result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {///gets the user
						User user = tweet.getUser();
						TweeterModel usertweet=new TweeterModel();
						UserModel usermodel=new UserModel();
						long time=tweet.getCreatedAt().getTime();
						int day =(int) (time-System.currentTimeMillis())/1000/3600/24;
						if (0<day||day<K){
							usermodel.setdescription(user.getDescription());
							usermodel.setlocation(user.getLocation());
							usermodel.setname(user.getName());
							usermodel.setprofileurl(user.getProfileImageURL());
    		  				usrs.add(usermodel);
    		  				usertweet.settweetcontext(tweet.getText());
    		  				usertweet.setuserhandle(user.getScreenName());
    		  				String uid=String.valueOf(user.getId());
    		  				dc.InsertUsers(user.getScreenName(), uid, user.getProfileImageURL(), user.getDescription());
    		  				String tid=String.valueOf(tweet.getId());
    		  				dc.InsertTweets(tid, tweet.getText(), "1", "1", "1");
 				}
//						else{StreamingApi sta=new StreamingApi(twitter, username);
//						}
			}
		}
	}
	catch (Exception te) {
	     te.printStackTrace();
	             System.out.println("Failed to search tweets:" +te.getMessage());
	             System.exit(-1);
	        }
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("userinfo", usrs);
	JsonUserOperation ujson=new JsonUserOperation();
//JsonVenuesOperation vjson=new JsonVenuesOperation();
	map.put("venues:", Location);
	String testjson=ujson.JsonGenerate((HashMap<String, Object>) map);
	System.out.println(testjson);
	return testjson;
	}

}
