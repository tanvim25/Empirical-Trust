package se.analysis.models;

public class UserTimeNumAnsObject {
	
	public UserTimeNumAnsObject(int userid, double respTime, int numAns) {
		super();
		this.userid = userid;
		this.respTime = respTime;
		this.numAns = numAns;
	}
	int userid;
	double respTime;
	int numAns;
	public int getUserid() {
		return userid;
	}
	public double getRespTime() {
		return respTime;
	}
	public int getNumAns() {
		return numAns;
	}
}
