/**  
*UserModel  
*@author Yue Ma  
*@version 1.0 2014/03/24
*/ 

package mjk.model;
//The user model
public class UserModel {
private String name;
private String userid;
private String location;
private String profileurl;
private String description;
private String culocation;
public void setname(String name){
	this.name=name;
}
public void setuserid(String id){
	this.userid=id;
}
public void setlocation(String location){
	this.location=location;
}
public void setculocation(String culocation){
	this.culocation=culocation;
}
public void setprofileurl(String profileurl){
	this.profileurl=profileurl;
}
public void setdescription(String description){
	this.description=description;
}
public String toString(){
	String test= "name is : "+name+"/n userid is : "+userid+"location is "+location+profileurl+description;
	return test;
}

}
