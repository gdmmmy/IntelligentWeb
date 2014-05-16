package mjk.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import twitter4j.*;

public class StreamingListening {
	private static final int TOTAL_TWEETS = 1000;
    public List<Status> execute(String username) throws Exception {
         final BlockingQueue<Status> statuses = new LinkedBlockingQueue<Status>(10000); 
         InitConnectionTwitter tt = new InitConnectionTwitter();
 		 Twitter twitterConnection=null;
 		 twitterConnection= tt.init();
 		 TwitterStreamFactory factory=new TwitterStreamFactory(twitterConnection.getConfiguration());
 		 TwitterStream twitterStream = factory.getInstance();        
 		 final StatusListener listener = new StatusListener() {
           public void onStatus(Status status) {
                statuses.offer(status); // Add received status to the queue
            }
			@Override
			public void onException(Exception arg0) {
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
        };
        final FilterQuery fq = new FilterQuery();
        String keywords[] = {"plane"};
        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);
        final List<Status> collected = new ArrayList<Status>(TOTAL_TWEETS);
        while (collected.size() < TOTAL_TWEETS) {
        	final Status status = statuses.poll(10, TimeUnit.SECONDS); 

            if (status == null) {
            	continue;
            }
            collected.add(status);
        }
        twitterStream.shutdown();

        return collected;
    }
}
