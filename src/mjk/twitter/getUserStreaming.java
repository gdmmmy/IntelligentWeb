package mjk.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import mjk.model.JsonUserOperation;
import mjk.model.TweeterModel;
import mjk.model.UsersModel;
import mjk.model.VenuesModel;

public class getUserStreaming {
	ArrayList<UsersModel> usrs=new ArrayList();
	ArrayList<String>usname=new ArrayList();
	public String testjson;
	public getUserStreaming(List<VenuesModel> Location) throws TwitterException, InterruptedException{
		try{
		 for (int i=0;i<Location.size()-1;i++){
	        	double la1=(Location.get(i).getLatitude());
	        	double la2=(Location.get(i).getLatitude())+4;
	        	double lo1=(Location.get(i).getLongitude());
	        	double lo2=(Location.get(i).getLongitude())+4;
	        	
	        double[][] coordinate ={{lo1,la1},{lo2,la2}};
	        locationStreaming lS=new locationStreaming();
	        List<Status> mylist=lS.execute(coordinate);
	        for (Status tweet : mylist) {///gets the user
				User user = tweet.getUser();
				TweeterModel usertweet=new TweeterModel();
				UsersModel usermodel=new UsersModel();
				//TODO insert a user justify
					usermodel.setdescription(user.getDescription());
					usermodel.setlocation(user.getLocation());
					usermodel.setname(user.getName());
					usermodel.setprofileurl(user.getProfileImageURL());
					usermodel.setculocation(Location.get(i).getName());
					if(!usname.contains(user.getName()))
						{
							usrs.add(usermodel);
							usname.add(user.getName());
						}
	  				usertweet.settweetcontext(tweet.getText());
	  				usertweet.setuserhandle(user.getScreenName());
			}
		 }
		}
catch (Exception te) {
 te.printStackTrace();
         System.out.println("Failed to search tweets:" +te.getMessage());
         System.exit(-1);
    }
Map<String, Object> map = new HashMap<String, Object>();
map.put("userinfo", usrs);
map.put("venues", Location);  	
JsonUserOperation ujson=new JsonUserOperation();    
 testjson=ujson.JsonGenerate((HashMap<String, Object>) map);
System.out.println("testjsonis"+testjson);
		
	}
	}



