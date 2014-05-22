/**  
*VenuesModel  
*@author Yue Ma  
*@version 1.0 2014/03/24
*/ 

package mjk.model;
//the venue model
import fi.foyt.foursquare.api.entities.Category;

public class VenuesModel {
	private String username;
	private String venuesname;
//	private String photourl;
	private double latitude;
	private double longitude;
	private String category;
	private Long creattime;
	private String interest[];
	private String photolists;
	public void setusername(String username){
		this.username=username;
	}
	public void setvenuesname(String venuesname){
		this.venuesname=venuesname;
	}
//	public void setphotourl(String photourl){
//		this.photourl=photourl;
//	}
	public void setCoordinate(double latitude, double longitude){
		this.latitude=latitude;
		this.longitude=longitude;
	}
	public void setcategory(String category){
		
		this.category=category;
	}
	public void setcreattime(Long creattime){
		this.creattime=creattime;
	}
	public void setinterest(String interest[]){
		this.interest=interest;
	}
	public double getLongitude(){
		return longitude;
	}
	public double getLatitude(){
		return latitude;
	}
	public String getName(){
		return venuesname;
	}
//	public String getphoto(){
//		return photourl;
//	}
	public String getcategory(){
		return category;
	}
	public Long getcreattime(){
		return creattime;
	}
	public void setphotolists(String photolists){
		this.photolists=photolists;
	}
	public String toString(){
		String venue="The user name is "+username+
				"The venuesname is "+venuesname+photolists+"";
//				"PhotoUrl "+photourl+latitude+longitude+category+creattime;
		return venue;
	}
	
}
