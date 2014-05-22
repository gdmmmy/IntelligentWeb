package mjk.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mjk.foursquare.ConnectWithTwitter;
import mjk.model.JsonTweetsOperation;
import mjk.model.JsonVenuesOperation;
import mjk.model.TweeterModel;
import mjk.model.VenuesModel;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.URLEntity;
import twitter4j.User;

public class getVenueStreaming {
private static String shorturl;
private static VenuesModel vm;
private static List<VenuesModel> vms=new ArrayList<VenuesModel>();
private static TweeterModel usertweet;
private static List<TweeterModel> tms=new ArrayList<TweeterModel>();
public String streamingjson;
public getVenueStreaming(String[] usernames) throws Exception{
	InitConnectionTwitter tt = new InitConnectionTwitter();
	Twitter twitterConnection=null;
	twitterConnection= tt.init();
	usernameStreaming vs=new usernameStreaming();
//	String usernames[] = {"", ""};
	List<Status> mylist= vs.execute(usernames);
	for (Status tweet : mylist) {///gets the user
		User user = tweet.getUser();
		usertweet=new TweeterModel();
		
  	if(tweet.getURLEntities()!=null)
  		{
  			URLEntity[] urls = tweet.getURLEntities();
  			for(URLEntity url : urls){
  				shorturl=url.getURL();
  				System.out.println(shorturl);
  				ConnectWithTwitter square=new ConnectWithTwitter();
  				vm=square.ConnectTwitter(shorturl);
  				System.out.println(vm.getName());
  				usertweet.settime(tweet.getCreatedAt());
  				usertweet.settweetcontext(tweet.getText());
  				usertweet.setuserhandle(user.getScreenName());
 				vms.add(vm);
  				String sid=String.valueOf(tweet.getId());
		        }
		}
  		tms.add(usertweet);
//dc.closecon();
	}


	Map<String, Object> map = new HashMap<String, Object>();
	map.put("tweetsinfo", tms);
	JsonTweetsOperation ujson=new JsonTweetsOperation();
	JsonVenuesOperation vjson=new JsonVenuesOperation();
	map.put("venues", vms);
	streamingjson=ujson.JsonGenerate((HashMap<String, Object>) map);
	
}

}
