/**  
*FindNearbyLocation
*@author Yue Ma
*@version 1.0 2014/03/24  
*/ 
 
package mjk.foursquare;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mjk.model.DatabaseConnection;
import mjk.model.VenuesModel;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.Photo;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.Photos;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
//According to the venue name to search 4square input a name of venue
//return a list for venues name and coordinat
public class FindNearbyLocation {
	private String CLIENT_ID="XOD1B55FCWUQ1TYE4YFXT5MHNGKGZ4RVMDDYRUQC334OF2Q4";
	private String CLIENT_SECRET="P1KQAREYRMHCRJYEPAU5ZTVUHN5VTEZF0ME2KU2J1RJPFVTD";
	private String REGISTERED_REDIRECT_URI="https://www.facebook.com/gdmmmy";
	private String ACCESS_TOKEN="ZRVMKF2ULCKNCE4PHFS1FXDZBCSNVFAVPIHX52OQSJSLSOUY";
	FoursquareApi foursquareApi = new FoursquareApi(CLIENT_ID,CLIENT_SECRET,REGISTERED_REDIRECT_URI);
	private String photolists;
	private String ca;
	 public FindNearbyLocation (){
		 foursquareApi.setoAuthToken(ACCESS_TOKEN);
		
	 }
	
	public ArrayList<VenuesModel> authenticationRequest(String venuename) throws FoursquareApiException, SQLException {
		/** use one venue name to search the nearby venues  
		* @param Venue Name  
		* @exception 
		* @return return_value  ArrayList<VenuesModel>  
		*/
		
	    ArrayList<VenuesModel>vms=new ArrayList<VenuesModel>();
//	    DatabaseConnection dc=new DatabaseConnection();
	    
	    Result<VenuesSearchResult> result = foursquareApi.venuesSearch(venuename, null, null, null, null, null, null, null);
	        if (result.getMeta().getCode() == 200) {
	          for (CompactVenue venue : result.getResult().getVenues()) {
	        	  	VenuesModel vm=new VenuesModel();
	        	  	CompleteVenue transfervenue=compactVenueToCompeleteVenue(venue);
	        	  	if(!transfervenue.equals(null)){
	        	  		List<String> photolist=getPhoto(transfervenue);
	        	  		photolists="";
	        	  		if(photolist!=null){
	        	  			for (int i=0;i<photolist.size();i++){
	        	  				photolists=photolists+photolist.get(i)+";";
//	        	  				System.out.println(photolists);
	        	  			}    				
		            	 }
	        	  	}
	        	
	            if (venue.getId().isEmpty()){
	            	ca="";
	            	if(venue.getCategories().length > 0) {
	            		for (int i=0;i<venue.getCategories().length;i++){
	            			ca=ca+venue.getCategories()[i].getName()+";";
	            		}			
	        		}
	            }
//	            System.out.println(photolists);
//	            System.out.println(ca);
	            vm.setphotolists(photolists);
	            vm.setcategory(ca);
//	            System.out.println(ca);
//	            vm.setphotourl(venue.getUrl());
	            vm.setCoordinate(venue.getLocation().getLat(),venue.getLocation().getLng());
	            vm.setvenuesname(venue.getName());
	            vms.add(vm);
//	            dc.InsertVenue(vm);
	            
	          }

	        } else {
	          System.out.println("Error occured: ");

	          System.out.println("  code: " + result.getMeta().getCode());

	          System.out.println("  type: " + result.getMeta().getErrorType());

	          System.out.println("  detail: " + result.getMeta().getErrorDetail()); 

	        	}

	        return vms;
	}

	public CompleteVenue compactVenueToCompeleteVenue(CompactVenue venue1) {
		CompleteVenue outPutVenue = null;
		try {
			Result<CompleteVenue> result1 = foursquareApi.venue(venue1.getId());
			if (result1.getMeta().getCode() == 200) {
				outPutVenue = result1.getResult();
			} else {
				System.out.println("Error occured: ");
				System.out.println("  code: " + result1.getMeta().getCode());
				System.out.println("  type: " + result1.getMeta().getErrorType());
				System.out.println("  detail: " + result1.getMeta().getErrorDetail());
				/*
				 * throw new IllegalStateException(
				 * "Four Square cannot find CompleteLocation");
				 */
			}
		} catch (FoursquareApiException e) {
			e.printStackTrace();
		}
		return outPutVenue;
	}
	public List<String> getPhoto(CompleteVenue venue) {
		List<String> photoList = new ArrayList<String>();
		PhotoGroup[] test = venue.getPhotos().getGroups();
		for (PhotoGroup tt : test) {
			if (tt.getItems() != null) {
				Photo[] temp = tt.getItems();
				for (Photo ab : temp) {
					photoList.add(ab.getUrl());
				}
			}
		}
		if (photoList.size() > 0)
			return photoList;
		else
			return null;
	}
}
