package mjk.twitter;

import twitter4j.*;

import twitter4j.conf.ConfigurationBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mjk.model.ContactsModel;
import mjk.model.DatabaseConnection;
import mjk.model.JsonOperation;
import mjk.model.TweetModel;
import mjk.model.UsersModel;
import net.sf.json.JSONArray;

import com.google.gson.Gson;

/** 
* Find specific discussion Twitter  
* @author Jing Sizhe, Team MJK
* @version 2.0, May 2014
*/

public class Specific_discussion {

		/**
		 * Queries Twitter for specified keywords and return a JSON string
		 * @param twitter twitter object used to call the Twitter API
		 * @param keyword keywords specified by user
		 * @param latitude latitude specified by user
		 * @param longitude longitude specified by user
		 * @return JSON string of discussion results from Twitter
		 * @throws SQLException if database connection fails
		 */
		public String getSimpleTimeLine(Twitter twitter, String keyword, String latitude, String longitude) throws SQLException
		{
			
			DatabaseConnection database = new DatabaseConnection();   
			
			try {
				// it creates a query and sets the geocode if latitude and longitude are provided
				Query query= new Query(keyword);
				// if the latitude and longitude are not null, set the location
				if(latitude !="" && longitude !="")
					query.setGeoCode(new GeoLocation(Long.valueOf(latitude), Long.valueOf(longitude)), 20,Query.KILOMETERS);
				//it fires the query
				query.setCount(100);  // number of limiting the query 
				
				QueryResult result = twitter.search(query);
				
				//it cycles on the tweets
				List<Status> tweets = result.getTweets();
				
				
				for (Status tweet : tweets) 
				{ ///gets the user
					User user = tweet.getUser();
						if(tweet.isRetweet()==false)
						{
							TweetModel tm = new TweetModel();
							// Insert to the database
							// put the text of tweet into arraylist
							String user_screenname = String.valueOf(user.getScreenName());
							String tweet_time = String.valueOf(tweet.getCreatedAt());
							String tweet_sid = String.valueOf(tweet.getId());
							String tweet_isretweet = null;
							tm.settweetcontext(tweet.getText());
							tm.settweetsid(tweet_sid);
							tm.setuserhandle(user_screenname);
							tm.settime(tweet_time);
							
							// judge the tweet is retweet or not
							if(tweet.isRetweet() == true)
							{
								tweet_isretweet = "1";
								tm.setisretweet("1");
							}
							else
							{
								tweet_isretweet = "0";
								tm.setisretweet("0");
							}
							database.InsertTweets(user_screenname, tweet.getText(), tweet_time, tweet_sid, tweet_isretweet);
							
							
							// Find retweeters
							List<Status> tweets1 = twitter.getRetweets(tweet.getId());
							// Use the ID of the tweet to search again and get retweets
							for (Status tweet1 : tweets1) 
							{
								UsersModel um = new UsersModel();
								ContactsModel cm = new ContactsModel();
								User user1 = tweet1.getUser();
								
								if(tweet1.isRetweet()==true)
								{// if this tweet is retweet and get its username
									if(ums.size()<10)
									{
										um.setname(user1.getName());
										um.setscreenname(user1.getScreenName());
										um.setlocation(user1.getLocation());
										um.setprofileurl(user1.getProfileImageURL());
										um.setdescription(user1.getDescription());
										ums.add(um);
										tm.setretweeter(user1.getScreenName());
										cm.setuserscreenname(user_screenname);
										cm.setpeoplescreenname(user1.getScreenName());
										cms.add(cm);
										database.InsertUsers(user1.getName(),user1.getScreenName(), user1.getLocation(), 
												user1.getProfileImageURL(), user1.getDescription());
										UsersTweets(twitter,user1.getScreenName());
										database.InsertContacts(user.getScreenName(), user1.getScreenName());
										// insert the user data into database
									}
								}

							}
							
							tms.add(tm);
						}
				}
				
				
				
			
			}
				catch (Exception te) 
				{
					te.printStackTrace();
					System.out.println("Failed to search tweets:" +
					te.getMessage());
					System.exit(-1);
				}
				
			JsonOperation ujson=new JsonOperation();
      		
	      	
				result.put("tweet", tms);
				JsonString=ujson.JsonGenerate((HashMap<String, Object>) result);
//				JsonArray.add(JsonString);
//				result.clear();
//				result.put("user", ums);
//				JsonString=ujson.JsonGenerate((HashMap<String, Object>) result);
//				JsonArray.add(JsonString);
//				result.clear();
//				result.put("contact",cms);
//				JsonString=ujson.JsonGenerate((HashMap<String, Object>) result);
//				JsonArray.add(JsonString);
				
				Gson gson = new Gson();    // make the jsonarray a json string
			
				System.out.println(JsonString);
				return JsonString;
			}
		
		
		public void UsersTweets(Twitter twitter,String userscreenname) throws SQLException, TwitterException
		{
			DatabaseConnection database = new DatabaseConnection();   
			Query query= new Query("from:"+userscreenname);
			
			query.setCount(100);  // number of limiting the query 
			
			QueryResult result = twitter.search(query);
			
			//it cycles on the tweets
			List<Status> tweets = result.getTweets();
			
			
			for (Status tweet : tweets) 
			{ ///gets the user
				User user = tweet.getUser();
				
				String user_screenname = String.valueOf(user.getScreenName());
				String tweet_time = String.valueOf(tweet.getCreatedAt());
				String tweet_sid = String.valueOf(tweet.getId());
				String tweet_isretweet = null;
				if(tweet.isRetweet() == true)
					tweet_isretweet = "1";
				else
					tweet_isretweet = "0";
				database.InsertTweets(user_screenname, tweet.getText(), tweet_time, tweet_sid, tweet_isretweet);
			}
			
		}
		private String JsonString;
		private ArrayList<ContactsModel> cms = new ArrayList<ContactsModel>();
		private ArrayList<UsersModel> ums = new ArrayList<UsersModel>();
		private ArrayList<TweetModel> tms = new ArrayList<TweetModel>();
		private Map<String,Object> result = new HashMap();
		
		private ArrayList<String> JsonArray = new ArrayList();
	}
	
