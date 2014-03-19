package mjk.foursquare;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mjk.model.DatabaseConnection;
import mjk.model.UserModel;
import mjk.model.VenuesModel;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactUser;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
public class FindNearbyLocation {
	private String CLIENT_ID="XOD1B55FCWUQ1TYE4YFXT5MHNGKGZ4RVMDDYRUQC334OF2Q4";
	private String CLIENT_SECRET="P1KQAREYRMHCRJYEPAU5ZTVUHN5VTEZF0ME2KU2J1RJPFVTD";
	private String REGISTERED_REDIRECT_URI="https://www.facebook.com/gdmmmy";
	private String ACCESS_TOKEN="ZRVMKF2ULCKNCE4PHFS1FXDZBCSNVFAVPIHX52OQSJSLSOUY";
	
	public ArrayList<VenuesModel> authenticationRequest(String venuename) throws FoursquareApiException, SQLException {
		FoursquareApi foursquareApi = new FoursquareApi(CLIENT_ID,CLIENT_SECRET,REGISTERED_REDIRECT_URI);
	    foursquareApi.setoAuthToken(ACCESS_TOKEN);
//	    ArrayList<double[]> locations=new ArrayList();
	    ArrayList<VenuesModel>vms=new ArrayList();
	   VenuesModel vm=new VenuesModel();
	    Result<VenuesSearchResult> result = foursquareApi.venuesSearch(venuename, null, null, null, null, null, null, null);
	        if (result.getMeta().getCode() == 200) {
          // if query was ok we can finally we do something with the data
	          for (CompactVenue venue : result.getResult().getVenues()) {

//	            locations.add(coordinate);
	            if (venue.getId()!=null){
	            vm.setcategory(venue.getCategories());
	            vm.setphotourl(venue.getUrl());
	            vm.setCoordinate(venue.getLocation().getLat(),venue.getLocation().getLng());
	            System.out.println(vm.getLatitude());
	            vm.setvenuesname(venue.getName());
	            vms.add(vm);
	            DatabaseConnection dc=new DatabaseConnection();
			    dc.InsertVenue(vm);
	            }
	          }

	        } else {
	          // TODO: Proper error handling
	          System.out.println("Error occured: ");

	          System.out.println("  code: " + result.getMeta().getCode());

	          System.out.println("  type: " + result.getMeta().getErrorType());

	          System.out.println("  detail: " + result.getMeta().getErrorDetail()); 

	        	}
	        return vms;
	    }
	
	
}
