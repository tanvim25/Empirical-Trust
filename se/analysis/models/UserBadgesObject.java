package se.analysis.models;

public class UserBadgesObject {

	public UserBadgesObject(int userId, int noOfBadges) {
		super();
		this.userId = userId;
		this.noOfBadges = noOfBadges;
	}
	int userId;
	int noOfBadges;
	public int getUserId() {
		return userId;
	}
	public int getNoOfBadges() {
		return noOfBadges;
	}
	
}
