/**  
*InitConnectionTwitter  
*@author Yue Ma  
*@version 1.0 2014/03/24  
*/

package mjk.twitter;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public class InitConnectionTwitter {
	//This class for initConnection with Twitter and find the data
			public ConfigurationBuilder cb;
			FileInputStream fis =null;
			public Twitter init() throws Exception{
			   /** Init Connection with Titter  
			   * @param 
			   * @exception exception
			   * @return twitterConnection
			   */
			   //set twitter token function
				Properties prop=new Properties();
				fis = new FileInputStream("/Users/mayue/Documents/workspaceforweb/MJKTwitterQuery/src/mjk/conncetion.properties");
				prop.load(fis);
				
				String consumerkey = "qTSL9WhT0xgFyu3DcNwpw";
				String consumersecret = "pBAAt8i2JNwgS56k6pU3BJwPaY4vcqVbzHEhjU6qjo";
				String accesstoken = "52638688-lDEtlEcLcQWF9e3EFVQb1ZAbw0L6M58k89u25nsoX";
				String accesstokensecret = "B1ENUPXkSzsnUVBVzL1DgVxWWo0iXr8dWRnru20z8NJYb";
				Twitter twitterConnection = initTwitter(consumerkey,consumersecret, accesstoken, accesstokensecret);
				return twitterConnection;
			}
			private Twitter initTwitter(String consumerKey, String consumerSecret,String accessToken, String accessTokenSecret)
				throws Exception {
				/** Init Connection with Titter  
				* @param String consumerKey, String consumerSecret,String accessToken, String accessTokenSecret  
				* @exception twitter exception
				* @return configure.getInstance
				*/
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
