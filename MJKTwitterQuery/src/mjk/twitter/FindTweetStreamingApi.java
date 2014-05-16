/**  
*FindTweetStreamingApi  
*@author Yue Ma  
*@version 1.0 2014/03/24  
*/

package mjk.twitter;
//streaming api to find tweet by using box of location
//print out line each json and insert it into database
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import mjk.model.DatabaseConnection;
import mjk.model.JsonUserOperation;
import mjk.model.TweeterModel;
import mjk.model.UserModel;
import mjk.model.VenuesModel;
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;

public class FindTweetStreamingApi {
	
	public static ArrayList<String> arraystr;
	
	public FindTweetStreamingApi(Twitter twitter,final List<VenuesModel>vms) throws Exception{
		/** Find Tweet Streaming Api
		* @param List<VenuesModel>l ,Twitter twitter
		* @exception   
		* @return jsonstring
		*/
//		final int TOTAL_TWEETS = 1000;
//		final BlockingQueue<Status> statuses = new LinkedBlockingQueue<Status>(10000);
		StatusListener list = new StatusListener()
		{
		private DatabaseConnection dc = new DatabaseConnection();;

		@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}
			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses)
			{
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}
			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onStatus(twitter4j.Status status) {
				arraystr = new ArrayList<String>();
//				statuses.offer(status);
				ArrayList<UserModel> usrs=new ArrayList<UserModel>();
				try {
						status.getUser();
						TweeterModel usertweet=new TweeterModel();
						UserModel usermodel=new UserModel();
						usermodel.setdescription(status.getUser().getDescription());
						usermodel.setlocation(status.getUser().getLocation());
						usermodel.setname(status.getUser().getName());
						usermodel.setprofileurl(status.getUser().getProfileImageURL());
			    		usrs.add(usermodel);
			    		usertweet.settweetcontext(status.getText());
			    		usertweet.setuserhandle(status.getUser().getScreenName());
			    		dc.InsertUsers(status.getUser().getName(), status.getUser().getScreenName(), status.getUser().getLocation(),status.getUser().getProfileImageURL(), status.getUser().getDescription());
		  				dc.InsertTweets(status.getUser().getScreenName(), status.getText(), String.valueOf(status.getCreatedAt()), 
		  				String.valueOf(status.getId()), null);
					}
				catch (Exception te) {
				     te.printStackTrace();
				             System.out.println("Failed to search tweets:" +te.getMessage());
				             System.exit(-1);
				        }
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userinfo", usrs);
				JsonUserOperation ujson=new JsonUserOperation();
				map.put("venues:", vms);
				String testjson=ujson.JsonGenerate((HashMap<String, Object>) map);
				
				System.out.println(testjson);
				arraystr.add(testjson);
			}
		};
        TwitterStreamFactory factory=new TwitterStreamFactory(twitter.getConfiguration());
        TwitterStream twitterStream = factory.getInstance();
        twitterStream.addListener(list);
        for (int i=0;i<vms.size()-1;i++){
        	double la1=(vms.get(i).getLatitude());
        	double la2=(vms.get(i).getLatitude())+4;
        	double lo1=(vms.get(i).getLongitude());
        	double lo2=(vms.get(i).getLongitude())+4;
        	
        double[][] coordinate ={{lo1,la1},{lo2,la2}};
        FilterQuery fq=new FilterQuery();
    	fq.locations(coordinate);
    	twitterStream.filter(fq);
//    	 final List<Status> collected = new ArrayList<Status>(TOTAL_TWEETS);
//         while (collected.size() < TOTAL_TWEETS) {
//             final Status status = statuses.poll(10, TimeUnit.SECONDS); 
//            if (status == null) {
//                continue;
//             }
//             collected.add(status);
//         }
//         twitterStream.shutdown();
//         System.out.println("already shut down");
        }
        
        }
               
	}

	


