package mjk.model;

import fi.foyt.foursquare.api.entities.Category;

public class VenuesModel {
	private String username;
	private String venuesname;
	private String photourl;
	private double latitude;
	private double longtitude;
	private String category;
	private Long creattime;
	public void setusername(String username){
		this.username=username;
	}
	public void setvenuesname(String venuesname){
		this.venuesname=venuesname;
	}
	public void setphotourl(String photourl){
		this.photourl=photourl;
	}
	public void setCoordinate(double latitude, double longtitude){
		this.latitude=latitude;
		this.longtitude=longtitude;
	}
	public void setcategory(Category[] categories){
		this.category=categories.toString();
	}
	public void setcreattime(Long creattime){
		this.creattime=creattime;
	}
	public double getLongtitude(){
		return longtitude;
	}
	public double getLatitude(){
		return latitude;
	}
	public String getName(){
		return venuesname;
	}
	public String getphoto(){
		return photourl;
	}
	public String getcategory(){
		return category;
	}
	public Long getcreattime(){
		return creattime;
	}
	public String toString(){
		String venue="The user name is "+username+
				"The venuesname is "+venuesname+
				"PhotoUrl "+photourl+latitude+longtitude+category+creattime;
		return venue;
	}
}
