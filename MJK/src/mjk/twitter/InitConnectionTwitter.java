package mjk.twitter;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
public class InitConnectionTwitter {
	//This class for initConnection with Twitter and find the data
			public ConfigurationBuilder cb;
		   Twitter init() throws Exception{
			   //set twitter token function
				String consumerkey = "qTSL9WhT0xgFyu3DcNwpw";
				String consumersecret = "pBAAt8i2JNwgS56k6pU3BJwPaY4vcqVbzHEhjU6qjo";
				String accesstoken = "52638688-lDEtlEcLcQWF9e3EFVQb1ZAbw0L6M58k89u25nsoX";
				String accesstokensecret = "B1ENUPXkSzsnUVBVzL1DgVxWWo0iXr8dWRnru20z8NJYb";
				Twitter twitterConnection = initTwitter(consumerkey,consumersecret, accesstoken, accesstokensecret);
				return twitterConnection;
			}
			private Twitter initTwitter(String consumerKey, String consumerSecret,String accessToken, String accessTokenSecret)
				throws Exception {
				cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true)
				.setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret)
				.setJSONStoreEnabled(true);
				return (new TwitterFactory(cb.build()).getInstance());
			}

}
