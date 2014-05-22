package mjk.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class usernameStreaming {
	 private static final int TOTAL_TWEETS = 5;

	    public List<Status> execute(String usernames[]) throws TwitterException, InterruptedException {
	        // skipped for brevity...
	    	System.out.println("testtesttest");
	        // TODO: You may have to tweak the capacity of the queue, depends on the filter query
	        final BlockingQueue<Status> statuses = new LinkedBlockingQueue<Status>(10000); 
	        final StatusListener listener = new StatusListener() {
	        
	        	
	            public void onStatus(Status status) {
	                statuses.offer(status); // Add received status to the queue
//	                System.out.println(status.getText());
	                System.out.println(status.getUser().getName() + " : " + status.getText());
	                
	                
	            }

				@Override
				public void onException(Exception arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onDeletionNotice(StatusDeletionNotice arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onScrubGeo(long arg0, long arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStallWarning(StallWarning arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTrackLimitationNotice(int arg0) {
					// TODO Auto-generated method stub
					
				}

	            // etc...
	        };

	        final FilterQuery fq = new FilterQuery();
//	        final String usernames[] = {"X-Men", ""};
	        fq.track(usernames);
	        ConfigurationBuilder cb = new ConfigurationBuilder();
	        cb.setDebugEnabled(true);
	        cb.setOAuthConsumerKey("MRX9drqFnPLilSaimlxZJsr5F");
	        cb.setOAuthConsumerSecret("pH418XARjDW07nKUOq3xyCwSYdkrlM1TKQvM3wzbJr1n0TXauz");
	        cb.setOAuthAccessToken("52638688-lDEtlEcLcQWF9e3EFVQb1ZAbw0L6M58k89u25nsoX");
	        cb.setOAuthAccessTokenSecret("B1ENUPXkSzsnUVBVzL1DgVxWWo0iXr8dWRnru20z8NJYb");

	        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	        twitterStream.addListener(listener);
//	        twitterStream.sample();
	        twitterStream.filter(fq);
	        System.out.println("test");
	        // Collect the 1000 statues
	        final List<Status> collected = new ArrayList<Status>(TOTAL_TWEETS);
	        while (collected.size() < TOTAL_TWEETS) {
	            // TODO: Handle InterruptedException
	            final Status status = statuses.poll(10, TimeUnit.SECONDS); 

	            if (status == null) {
	                // TODO: Consider hitting this too often could indicate no further Tweets
	                continue;
	            }
	            collected.add(status);
	        }
	        twitterStream.shutdown();

	        return collected;
	    }
}
