package mjk.model;

import java.util.Date;

public class TweeterModel {
	private String userhandle;
	private String tweetcontext;
	private Date time;
	public void setuserhandle(String userhandle){
		this.userhandle=userhandle;
	}
	public void settweetcontext(String tweetcontext){
		this.tweetcontext=tweetcontext;
	}
	public void settime(Date date){
		this.time=date;
	}
	public String toString(){
		String tweet=userhandle+tweetcontext+time;
		return tweet;
	}
}
