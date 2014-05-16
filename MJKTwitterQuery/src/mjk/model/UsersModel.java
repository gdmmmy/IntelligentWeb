package mjk.model;

/** 
* Model for Users
* @author Jing Sizhe, Team MJK
* @version 2.0, May 2014
*/
public class UsersModel {
private String name;
private String screenname;
private String location;
private String profileurl;
private String description;
private String culocation;

/**
 * sets the name of user
 */
public void setname(String name){
	this.name=name;
}

/**
 * sets the screenname of user
 */
public void setscreenname(String screenname){
	this.screenname=screenname;
}

/**
 * sets the location of user
 */
public void setlocation(String location){
	this.location=location;
}

/**
 * sets the profileurl of user
 */
public void setprofileurl(String profileurl){
	this.profileurl=profileurl;
}

/**
 * sets the description of user
 */
public void setdescription(String description){
	this.description=description;
}
public void setculocation(String culocation){
	this.culocation=culocation;
}
}
