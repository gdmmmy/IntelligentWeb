/**  
*CheckinModel
*@author Yue Ma
*@version 1.0 2014/03/24  
*/ 

package mjk.model;
//check in model

public class CheckinModel {
	/** Checking Model in Foursquare
	* @param venueid, userid, checkintime
	* @exception   
	* @return  
	*/ 
	private String venueid;
	private String userid;
	private String checkintime;
	
	public void setusername(String venueid){
		
		this.venueid=venueid;
	}
	public void setuserid(String userid){
		this.userid=userid;
	}
	public void setcheckintime(String checkintime){
		this.checkintime=checkintime;
	}
	public String toString(){
		String checkin=venueid+userid+checkintime;
		return checkin;
	}
}
