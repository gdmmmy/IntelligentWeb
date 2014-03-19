package mjk.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mjk.model.VenuesModel;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class FindTweetStreamingApi {
	public FindTweetStreamingApi(Twitter twitter,List<VenuesModel>vms) throws Exception{
//        if (args.length < 1) {
//            System.out.println("Usage: java twitter4j.examples.PrintFilterStream [follow(comma separated numerical user ids)] [track(comma separated filter terms)]");
//            System.exit(-1);
//        }
		StatusListener list = new StatusListener()
		{
			
		//	@Override public void onStatus(Status status) {
			//	System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			//	}
			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				ex.printStackTrace();
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}
			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses)
			{
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}
			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// TODO Auto-generated method stub
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub
				System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onStatus(twitter4j.Status status) {
				// TODO Auto-generated method stub
			//	String user = "oOsYAza";
			//	Query query = new Query("from:"+user);
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			//	System.out.println("@" +user());
			}
		};
        TwitterStreamFactory factory=new TwitterStreamFactory(twitter.getConfiguration());
        TwitterStream twitterStream = factory.getInstance();
        twitterStream.addListener(list);
        double[][] coordinate = new double[vms.size()][vms.size()];
        for(int i=0;i<vms.size();i++){  
        	coordinate[i][0]=vms.get(i).getLongtitude();
        	for(int j=0;j<vms.size();j++){  
            	coordinate[i][j]=vms.get(j).getLongtitude();  
        }
            }
        
//        double[][] coordinate ={{d,c},{f,e}};
// sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
	
    FilterQuery fq=new FilterQuery();
	fq.locations(coordinate);
	twitterStream.filter(fq);
	}

	}


