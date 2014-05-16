/**  
*RSETAPI  
*@author Yue Ma  
*@version 1.0 2014/03/24  
*/

package mjk.twitter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mjk.foursquare.ConnectWithTwitter;
import mjk.model.DatabaseConnection;
//import mjk.model.DatabaseConnection;
import mjk.model.JsonTweetsOperation;
import mjk.model.JsonUserOperation;
import mjk.model.JsonVenuesOperation;
import mjk.model.TweeterModel;
import mjk.model.UserModel;
import mjk.model.UsersModel;
import mjk.model.VenuesModel;
import twitter4j.*;
import fi.foyt.foursquare.api.FoursquareApiException;

public class RSETAPI {

	private String shorturl;
//	private DatabaseConnection dc;
	private List<TweeterModel> tms = new ArrayList<TweeterModel>();
	private List<VenuesModel>vms=new ArrayList<VenuesModel>();
	private VenuesModel vm;
	//According to specific user to find his/her twitter and then connect with foursquare to check venue
	public String FindLocation(int K,String username) throws FoursquareApiException, SQLException{
		/** Find Location for specific user 
		* @param K,username,twitter
		* @exception FoursquareApiException, SQLException
		* @return return JSON String
		*/
//		dc = new DatabaseConnection();
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
    		  				usertweet.setuserhandle(user.getScreenName());
    		  				String tweettime=tweet.getCreatedAt().toGMTString();
    		  				String tweetid=Long.toString(tweet.getId());
    		  				vms.add(vm);
    		  				String sid=String.valueOf(tweet.getId());
    		  				String id=String.valueOf(user.getId());
//    		  				dc.InsertTweets(user.getScreenName(), tweet.getText(), tweettime, 
//    		  						tweetid, "0");
    		  				
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
        	JsonVenuesOperation vjson=new JsonVenuesOperation();
        	map.put("venues", vms);
        	String testjson=ujson.JsonGenerate((HashMap<String, Object>) map);
        	System.out.println(testjson);
        	return testjson;
	}

public String findTweets(Twitter twitter,int K,List<VenuesModel> Location) throws SQLException{
	/** Find tweets for boxes of location 
	* @param Twitter twitter,int K,List<VenuesModel> Location
	* @exception SQLException
	* @return return JSON String
	*/
	DatabaseConnection dc=new DatabaseConnection();
	ArrayList<UsersModel> usrs=new ArrayList();
	ArrayList<String>usname=new ArrayList();
	try {
			int index=0;
			for(index=0;index<Location.size();index++){
				Query query= new Query();
				query.setGeoCode(new GeoLocation(Location.get(index).getLatitude(),
						Location.get(index).getLongitude())
				,2,query.KILOMETERS);
				QueryResult result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {///gets the user
						User user = tweet.getUser();
						TweeterModel usertweet=new TweeterModel();
						UsersModel usermodel=new UsersModel();
						long time=tweet.getCreatedAt().getTime();
						int day =(int) (time-System.currentTimeMillis())/1000/3600/24;
						if (0<day||day<K){
							
							//TODO insert a user justify
							usermodel.setdescription(user.getDescription());
							usermodel.setlocation(user.getLocation());
							usermodel.setname(user.getName());
							usermodel.setprofileurl(user.getProfileImageURL());
							usermodel.setculocation(Location.get(index).getName());
							if(!usname.contains(user.getName()))
								{
									usrs.add(usermodel);
									usname.add(user.getName());
								}
    		  				usertweet.settweetcontext(tweet.getText());
    		  				usertweet.setuserhandle(user.getScreenName());
    		  				String uid=String.valueOf(user.getId());
    		  				dc.InsertUsers(user.getName(), user.getScreenName(),user.getLocation() , user.getProfileImageURL(), user.getDescription());
    		  				String tid=String.valueOf(tweet.getId());
    		  				dc.InsertTweets(user.getScreenName(), tweet.getText(), String.valueOf(tweet.getCreatedAt()), tid,null);
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
	map.put("venues", Location);  	
	JsonUserOperation ujson=new JsonUserOperation();    
	String testjson=ujson.JsonGenerate((HashMap<String, Object>) map);
	System.out.println("testjsonis"+testjson);
	return testjson;
	}

}
