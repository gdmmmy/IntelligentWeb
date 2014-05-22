
package mjk.model;
import java.util.ArrayList;
//Tweet model
import java.util.Date;
/** 
* Model for tweets
* @author Jing Sizhe, Team MJK
* @version 2.0, May 2014
*/

public class TweetModel {
	private String userhandle;
	private String tweetsid;
	private String tweetcontext;
	private String time;
	private String isretweet;
	private ArrayList<String> retweeter = new ArrayList();
	
	/**
	 * sets the id of tweet
	 */
	public void settweetsid(String tweetsid){
		this.tweetsid=tweetsid;
	}
	
	/**
	 * sets the userhandle of tweet
	 */
	public void setuserhandle(String userhandle){
		this.userhandle=userhandle;
	}
	
	/**
	 * sets the context of tweet
	 */
	public void settweetcontext(String tweetcontext){
		this.tweetcontext=tweetcontext;
	}
	
	/**
	 * sets the time of tweet
	 */
	public void settime(String time){
		this.time=time;
	}
	
	/**
	 * sets the isretweet of tweet
	 */
	public void setisretweet(String b)
	{
		this.isretweet=b;
	}
	
	/**
	 * sets the list of people who retweeted this tweet
	 */
	public void setretweeter(String r)
	{
		this.retweeter.add(r);
	}
}
