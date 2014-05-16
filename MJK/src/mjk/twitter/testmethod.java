package mjk.twitter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mjk.foursquare.ConnectWithTwitter;
import mjk.model.DatabaseConnection;
import mjk.model.JsonUserOperation;
import mjk.model.TweeterModel;
import mjk.model.UserModel;
import mjk.model.VenuesModel;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;
import fi.foyt.foursquare.api.FoursquareApiException;

public class testmethod {
	private String shorturl;
	private DatabaseConnection dc;
	private List<UserModel> users = new ArrayList<UserModel>();
	private List<VenuesModel>vms=new ArrayList<VenuesModel>();
	private VenuesModel vm;
	private List<TweeterModel> tweets;
    private Status tweet;
    private Map<String, Object> map;
    private JsonUserOperation ujson;
    private String testjson;
	public void testmethod(){
				FindLocationStreamingApi sa=new FindLocationStreamingApi(usernames);
  				sa.vm.setdescription(user.getDescription());
  				usertweet.setlocation(user.getLocation());
  				usertweet.setname(user.getName());
  				usertweet.setprofileurl(user.getProfileImageURL());
  				vms.add(vm);
  				users.add(usertweet);
		    	map = new HashMap<String, Object>();
		      	ujson=new JsonUserOperation();
	      		map.put("venues:", vms);
		      	testjson=ujson.JsonGenerate((HashMap<String, Object>) map);
		      	System.out.println(testjson);
		        }
		}else{};
}
}
