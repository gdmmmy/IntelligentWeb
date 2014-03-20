package mjk.foursquare;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mjk.model.*;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CompactUser;
import fi.foyt.foursquare.api.entities.CompactVenue;
public class ConnectWithTwitter  {
	//This class for extending Twitter shortUrl to LongUrl and using it query foursquare
	private String CLIENT_ID="XOD1B55FCWUQ1TYE4YFXT5MHNGKGZ4RVMDDYRUQC334OF2Q4";
	private String CLIENT_SECRET="P1KQAREYRMHCRJYEPAU5ZTVUHN5VTEZF0ME2KU2J1RJPFVTD";
	private String REGISTERED_REDIRECT_URI="https://www.facebook.com/gdmmmy";
	private String ACCESS_TOKEN="ZRVMKF2ULCKNCE4PHFS1FXDZBCSNVFAVPIHX52OQSJSLSOUY";
	
		  public VenuesModel ConnectTwitter(String shortURLs) throws FoursquareApiException, SQLException {
		    FoursquareApi foursquareApi = new FoursquareApi(CLIENT_ID,CLIENT_SECRET,REGISTERED_REDIRECT_URI);
		    foursquareApi.setoAuthToken(ACCESS_TOKEN);
		    String url= expandUrl(shortURLs);
		    if (!((url.startsWith("https://foursquare.com/"))&&(url.contains("checkin"))&&(url.contains("s=")))) return null;
		    Pattern pId = Pattern.compile(".+?checkin/(.+?)\\?s=.+", Pattern.DOTALL);
		    Matcher matche = pId.matcher(url);
		    String checkInId = (matche.matches()) ? matche.group(1) : "";
		    Pattern pSig = Pattern.compile(".+?\\?s=(.*)\\&.+", Pattern.DOTALL);
		    matche = pSig.matcher(url);
		    String sig = (matche.matches()) ? matche.group(1) : "";
		    Result<Checkin> chck = null; 
		    try {
		    	chck = foursquareApi.checkin(checkInId, sig); 
		    	System.out.println(chck.getResult());}
		    catch (FoursquareApiException e) {
		    	e.printStackTrace();  }
		    VenuesModel vm=new VenuesModel();
		    Checkin cc = chck.getResult();
		    CompactUser user= cc.getUser();
		    System.out.print("CHECK IN: " + user.getFirstName() + " " + user.getLastName() + " (" + user.getGender());
		    System.out.print(") Just checked in at: ");
		    
		    CompactVenue venue= cc.getVenue();
		    System.out.print(" " + venue.getName());
		    vm.setcategory(venue.getCategories());
		    vm.setvenuesname(venue.getName());
		    vm.setcreattime(cc.getCreatedAt());
		    System.out.println(vm.toString());
		    DatabaseConnection dc=new DatabaseConnection();
		    dc.InsertVenue(vm);
		    return vm;
//		    
		   

		  }
		    private String expandUrl(String shortURLs) 
		    {
		    	String url = shortURLs;
			    String initialUrl = shortURLs;
			    while (url!=null){
			    	try {
			    		url = getFullURL(shortURLs);
			    		if (url!=null) shortURLs= url;
		    	else {
		    		url= shortURLs;
		    		break; }
		    } catch (IOException e) {
		    break;
		    }
		    }
				return url;
	}
		    private String getFullURL (String shortURLs) throws IOException { 
		    URL shortUrl= new URL(shortURLs);
		    final HttpURLConnection httpURLConnection =(HttpURLConnection) shortUrl.openConnection();
		    httpURLConnection.setInstanceFollowRedirects(false);
		    httpURLConnection.connect();
		    final String header = httpURLConnection.getHeaderField("Location");
		    return header;
		    }
}
