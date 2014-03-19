package mjk.model;

public class CheckinModel {
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
