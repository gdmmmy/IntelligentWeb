

package mjk.model;
import java.util.Date;
/** 
* Model for Contacts; contacts are created when users retweet each other's tweets.  
* @author Jing Sizhe, Team MJK
* @version 2.0, May 2014
*/

public class ContactsModel {
	private String userscreenname;
	private String peoplescreenname;
	
	/**
	 * Sets the screenname of the user who's tweet was retweeted by someone else.
	 * @param userscreenname screenname of the user who's tweet was retweeted by someone else.
	 */
	public void setuserscreenname(String userscreenname){
		this.userscreenname=userscreenname;
	}
	
	/**
	 * Sets the screenname of the user who has retweeted someone else's tweet.
	 * @param peoplescreenname screenname of the user who has retweeted someone else's tweet.
	 */
	public void setpeoplescreenname(String peoplescreenname){
		this.peoplescreenname=peoplescreenname;
	}

}
