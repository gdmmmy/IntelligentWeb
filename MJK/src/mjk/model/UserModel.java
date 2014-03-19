package mjk.model;

public class UserModel {
private String name;
private String userid;
private String location;
private String profileurl;
private String description;
public void setname(String name){
	this.name=name;
}
public void setuserid(String id){
	this.userid=id;
}
public void setlocation(String location){
	this.location=location;
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
