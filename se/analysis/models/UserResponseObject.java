package se.analysis.models;

public class UserResponseObject {
	
	public UserResponseObject(int userid, double resptime) {
		super();
		this.userid = userid;
		this.resptime = resptime;
	}
	int userid;
	double resptime;
	public int getUserid() {
		return userid;
	}
	public double getResptime() {
		return resptime;
	}

}
